package uz.itteacher.itschoolbank.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.itteacher.itschoolbank.model.*
import uz.itteacher.itschoolbank.repository.FirebaseRepository
import uz.itteacher.itschoolbank.service.FirebaseService

import uz.itteacher.itschoolbank.R
import uz.itteacher.itschoolbank.model.ChartCategory
import java.text.SimpleDateFormat
import java.util.*

class MainTransactionViewModel : ViewModel() {

    private val repo = FirebaseRepository()
    private val notificationService = FirebaseService()
    private val auth = FirebaseAuth.getInstance()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _dbTransactions = MutableStateFlow<List<DBTransaction>>(emptyList())
    private val _transactionsUi = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactionsUi

    private val _allUsers = MutableStateFlow<List<User>>(emptyList())
    val allUsers: StateFlow<List<User>> = _allUsers

    private val _requests = MutableStateFlow<List<Request>>(emptyList())
    val requests: StateFlow<List<Request>> = _requests

    private val _categories = MutableStateFlow(
        listOf(
            ChartCategory("Food", 35f, Color(0xFFE57373)),
            ChartCategory("Shopping", 25f, Color(0xFF64B5F6)),
            ChartCategory("Transport", 15f, Color(0xFF81C784)),
            ChartCategory("Bills", 10f, Color(0xFFFFB74D)),
            ChartCategory("Others", 15f, Color(0xFF9575CD))
        )
    )
    val categories = _categories


    init {
        startObserving()
    }

    private fun startObserving() {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            observeCurrentUser(uid as String)
            observeTransactions(uid)
            observeRequests(uid)
        }
        viewModelScope.launch {
            try {
                _allUsers.value = repo.getAllUsersOnce()
            } catch (_: Exception) { /* ignore */ }
        }
    }

    private fun observeCurrentUser(uid: String) {
        val usersRef = com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("users")
        usersRef.child(uid).addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val u = snapshot.getValue(User::class.java)
                _currentUser.value = u
            }
            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
        })
    }

    private fun observeTransactions(uid: String) {
        val transactionsRef = com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("transactions")
        transactionsRef.orderByChild("ownerUid").equalTo(uid)
            .addValueEventListener(object : com.google.firebase.database.ValueEventListener {
                override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                    val list = snapshot.children.mapNotNull { it.getValue(DBTransaction::class.java) }
                    _dbTransactions.value = list.sortedByDescending { it.date }
                    _transactionsUi.value = _dbTransactions.value.map { mapDbToUi(it) }
                }
                override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
            })
    }

    private fun observeRequests(uid: String) {
        val requestsRef = com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("requests")
        requestsRef.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                val list = snapshot.children.mapNotNull { it.getValue(Request::class.java) }
                val uidLocal = auth.currentUser?.uid
                _requests.value = list.filter { it.toUid == uidLocal || it.fromUid == uidLocal }
            }
            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
        })
    }

    fun reloadUsers(onResult: (List<User>) -> Unit) {
        viewModelScope.launch {
            try {
                val list = repo.getAllUsersOnce()
                _allUsers.value = list
                onResult(list)
            } catch (e: Exception) {
                onResult(emptyList())
            }
        }
    }

    /**
     * Send money:
     * 1) atomically decrement sender (suspend)
     * 2) atomically increment receiver (suspend)
     * 3) write two DBTransaction records
     * 4) create notification record
     */
    fun sendMoney(toUid: String, amount: Double, description: String = "", category: String = "transfer", iconKey: String = "transfer", onResult: (Boolean, String?) -> Unit = { _, _ -> }) {
        val fromUid = auth.currentUser?.uid
        if (fromUid == null) { onResult(false, "Not signed-in"); return }
        viewModelScope.launch {
            try {
                // 1) decrement sender
                val okSender = repo.runBalanceTransactionSuspend(fromUid as String, -amount)
                if (!okSender) { onResult(false, "Insufficient funds"); return@launch }

                // 2) increment receiver
                val okReceiver = repo.runBalanceTransactionSuspend(toUid, +amount)
                if (!okReceiver) {
                    // rollback sender
                    repo.runBalanceTransactionSuspend(fromUid as String, +amount)
                    onResult(false, "Receiver update failed")
                    return@launch
                }

                // 3) write tx records
                val now = System.currentTimeMillis()
                val txId1 = com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("transactions").push().key ?: ""
                val txId2 = com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("transactions").push().key ?: ""
                val txSender = DBTransaction(id = txId1, ownerUid = fromUid as String, name = "Sent to $toUid", category = category, amount = -amount, iconKey = iconKey, date = now)
                val txReceiver = DBTransaction(id = txId2, ownerUid = toUid, name = "Received from $fromUid", category = category, amount = amount, iconKey = iconKey, date = now)
                com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("transactions").child(txSender.id).setValue(txSender)
                com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("transactions").child(txReceiver.id).setValue(txReceiver)

                // 4) notify receiver (DB record)
                val senderName = _currentUser.value?.name ?: "Someone"
                notificationService.sendNotification(toUid, "You received money", "$senderName sent you $${"%.2f".format(amount)}")

                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    // Create Payment Request
    fun createRequest(toUid: String, amount: Double, message: String = "", onResult: (Boolean, String?) -> Unit = { _, _ -> }) {
        val fromUid = auth.currentUser?.uid ?: run { onResult(false, "Not signed-in"); return }
        viewModelScope.launch {
            try {
                val key = com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("requests").push().key ?: ""
                val req = Request(id = key, fromUid = fromUid, toUid = toUid, amount = amount, message = message)
                com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("requests").child(key).setValue(req)
                val me = _currentUser.value?.name ?: "Someone"
                notificationService.sendNotification(toUid, "Payment Request", "$me requested $${"%.2f".format(amount)}")
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun approveRequest(request: Request, onResult: (Boolean, String?) -> Unit = { _, _ -> }) {
        viewModelScope.launch {
            try {
                sendMoney(request.fromUid, request.amount, "Request payment", "request", "request") { success, err ->
                    if (success) {
                        com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("requests").child(request.id).child("status").setValue("approved")
                        onResult(true, null)
                    } else {
                        onResult(false, err)
                    }
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    fun rejectRequest(request: Request) {
        com.google.firebase.database.FirebaseDatabase.getInstance().reference.child("requests").child(request.id).child("status").setValue("rejected")
    }

    private fun mapDbToUi(db: DBTransaction): Transaction {
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        val dateStr = sdf.format(Date(db.date))
        val iconRes = iconKeyToDrawable(db.iconKey)
        return Transaction(id = db.id, name = db.name, category = db.category, amount = db.amount, iconRes = iconRes, date = dateStr)
    }

    private fun iconKeyToDrawable(iconKey: String): Int {
        return when (iconKey) {
            "apple" -> R.drawable.apple
            "spotify" -> R.drawable.spotify
            "transfer" -> R.drawable.transfer
            "shopping" -> R.drawable.shopping
            "request" -> R.drawable.request
            else -> R.drawable.ic_transaction
        }
    }

}
