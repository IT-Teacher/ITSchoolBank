package uz.itteacher.itschoolbank.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import uz.itteacher.itschoolbank.modul.BankCard
import uz.itteacher.itschoolbank.repository.CardRepository

class CardViewModel : ViewModel() {
    private val repository = CardRepository()
    val cards = mutableStateListOf(
        BankCard("1234567890123456", "Tanya Myroniuk", "24/2000", "6986", "Visa", 1000f),
    )

    init {
        loadCards()
    }

    fun loadCards() {
        cards.clear()
        cards.addAll(repository.getCards())
    }

    fun addCard(card: BankCard) {
        repository.addCard(card)
        loadCards()
    }

    fun updateCard(updatedCard: BankCard) {
        val index = cards.indexOfFirst { it.cardNumber == updatedCard.cardNumber }
        if (index != -1) {
            cards[index] = updatedCard
            repository.addCard(updatedCard)
        }
    }

    fun setMainCard(card: BankCard) {
        val index = cards.indexOf(card)
        if (index != -1 && index != cards.lastIndex) {
            cards.removeAt(index)
            cards.add(card)
            println("ViewModel moved card to main: ${card.cardNumber}") // Debug print
            repository.updateCards(cards.toList()) // Update repository
            loadCards() // Reload to sync with repository
        }
    }
}