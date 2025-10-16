package uz.itteacher.itschoolbank

class CardRepository {
    private val cards = mutableListOf<BankCard>()

    fun getCards(): List<BankCard> {
        return cards.toList()
    }

    fun addCard(card: BankCard) {
        cards.add(card)
    }
}