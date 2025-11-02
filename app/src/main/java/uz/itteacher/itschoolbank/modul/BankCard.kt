package uz.itteacher.itschoolbank.modul

class BankCard(
    val cardNumber: String,
    val cardholderName: String,
    val expiryDate: String,
    val cvv: String,
    val cardType: String = "Mastercard",
    val monthlyLimit: Float = 10000f,
    val spentAmount: Float = 0f,
    val transactions: List<Transaction> = emptyList(),
    val userId: String
){
    constructor() : this("","", "", "", "", 0f, 0f, emptyList(), "")
}

data class Transaction(
    val merchant: String,
    val amount: Float,
    val category: String
)