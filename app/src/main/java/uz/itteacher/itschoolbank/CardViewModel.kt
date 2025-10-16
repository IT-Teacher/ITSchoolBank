package uz.itteacher.itschoolbank

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CardViewModel : ViewModel() {
    private val repository = CardRepository()
    val cards = mutableStateListOf<BankCard>()

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
            repository.addCard(updatedCard) // Overwrite in repository
        }
    }

    fun setMainCard(card: BankCard) {
        val index = cards.indexOf(card)
        if (index != -1 && index != cards.lastIndex) {
            cards.removeAt(index)
            cards.add(card)
            repository.updateCards(cards) // Update repository with new order
        }
    }
}