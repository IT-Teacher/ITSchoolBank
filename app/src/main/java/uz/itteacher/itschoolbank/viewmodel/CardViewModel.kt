package uz.itteacher.itschoolbank.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uz.itteacher.itschoolbank.modul.BankCard
class CardViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _cards = MutableStateFlow<List<BankCard>>(emptyList())
    val cards: StateFlow<List<BankCard>> = _cards.asStateFlow()

    fun loadCards(userId: String) {
        db.collection("cards")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snap, _ ->
                val list = snap?.toObjects(BankCard::class.java) ?: emptyList()
                _cards.value = list
            }
    }
}