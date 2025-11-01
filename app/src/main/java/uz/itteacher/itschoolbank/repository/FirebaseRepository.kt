package uz.itteacher.itschoolbank.repository

import com.google.firebase.database.*
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import uz.itteacher.itschoolbank.model.DBTransaction
import uz.itteacher.itschoolbank.model.Request
import uz.itteacher.itschoolbank.model.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirebaseRepository {
    private val db = FirebaseDatabase.getInstance().reference
    private val usersRef = db.child("users")
    private val transactionsRef = db.child("transactions")
    private val requestsRef = db.child("requests")

    suspend fun getAllUsersOnce(): List<User> {
        val snap = usersRef.get().await()
        return snap.children.mapNotNull { it.getValue(User::class.java) }
    }

    suspend fun addTransaction(tx: DBTransaction) {
        val key = transactionsRef.push().key ?: throw Exception("push key null")
        transactionsRef.child(key).setValue(tx.copy(id = key)).await()
    }

    suspend fun addRequest(req: Request) {
        val key = requestsRef.push().key ?: throw Exception("push key null")
        requestsRef.child(key).setValue(req.copy(id = key)).await()
    }

    /**
     * Run transaction on user's balance atomically. Returns true if committed.
     */
    suspend fun runBalanceTransactionSuspend(userId: String, delta: Double): Boolean =
        suspendCancellableCoroutine { cont ->
            val ref = usersRef.child(userId).child("balance")
            ref.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val cur = when (val v = currentData.getValue()) {
                        is Long -> v.toDouble()
                        is Double -> v
                        is String -> v.toDoubleOrNull() ?: 0.0
                        else -> 0.0
                    }
                    val new = cur + delta
                    if (new < 0.0) return Transaction.abort()
                    currentData.value = new
                    return Transaction.success(currentData)
                }
                override fun onComplete(error: DatabaseError?, committed: Boolean, snapshot: DataSnapshot?) {
                    if (cont.isCancelled) return
                    if (error != null) cont.resume(false)
                    else cont.resume(committed)
                }
            })
            cont.invokeOnCancellation { /* no-op */ }
        }
}
