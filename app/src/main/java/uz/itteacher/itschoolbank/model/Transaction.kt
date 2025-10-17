package uz.itteacher.itschoolbank.model

data class Transaction(
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val iconRes: Int = 0,
    val date: String = ""
)

