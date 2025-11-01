package uz.itteacher.itschoolbank.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val balance: Double = 0.0,
    val profileImage: String = ""
)
