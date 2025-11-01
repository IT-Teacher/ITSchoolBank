package uz.itteacher.itschoolbank.model

data class Request(
    val id: String = "",
    val fromUid: String = "",
    val toUid: String = "",
    val amount: Double = 0.0,
    val message: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val status: String = "pending"
)
