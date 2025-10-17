package uz.itteacher.itschoolbank.repository

import uz.itteacher.itschoolbank.modul.BankCard

class CardRepository {
    private val cards = mutableListOf<BankCard>(
        BankCard("1234567890123456", "Tanya Myroniuk", "24/2000", "6986", "Visa", 1000f),
    )
    fun getCards(): List<BankCard> {
        return cards.toList()
    }

    fun addCard(card: BankCard) {
        cards.add(card)
    }

    fun updateCards(newCards: List<BankCard>) {
        cards.clear()
        cards.addAll(newCards)
        println("Repository updated with ${cards.size} cards, last card: ${cards.lastOrNull()?.cardNumber}") // Debug print
    }
}