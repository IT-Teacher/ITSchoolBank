package uz.itteacher.itschoolbank

class CardRepository {
    private val cards = mutableListOf<BankCard>()

//    init {
//        // Sample data
//        cards.add(BankCard("1234 5678 9012 3456", "John Doe", "12/25", "123", "Mastercard"))
//        cards.add(BankCard("9876 5432 1098 7654", "Jane Doe", "11/26", "456", "Visa"))
//    }

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