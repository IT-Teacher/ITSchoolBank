package uz.itteacher.itschoolbank.model

data class DBTransaction(
    val id: String = "",
    val ownerUid: String = "",
    val name: String = "",
    val category: String = "",
    val amount: Double = 0.0,
    val iconKey: String = "transfer",
    val date: Long = System.currentTimeMillis()
)
