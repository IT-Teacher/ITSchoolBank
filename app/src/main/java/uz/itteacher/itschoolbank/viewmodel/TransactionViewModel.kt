package uz.itteacher.itschoolbank.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color
import uz.itteacher.itschoolbank.model.Transaction
import uz.itteacher.itschoolbank.repository.TransactionRepository

data class ChartCategory(val name: String, val percent: Float, val color: Color)

class TransactionViewModel : ViewModel() {

    private val _transactions = mutableStateListOf<Transaction>()
    val transactions: List<Transaction> get() = _transactions

    // query endi oddiy public o'zgaruvchi bo'ladi
    var query by mutableStateOf("")

    var categories by mutableStateOf(listOf<ChartCategory>())
        private set

    init {
        load()
    }

    fun load() {
        _transactions.clear()
        _transactions.addAll(TransactionRepository.getTransactions())

        // Keyinchalik bu qismini Firebase'dan olingan ma'lumotlar asosida dinamik qilamiz
        categories = listOf(
            ChartCategory("Transaction", 55f, Color(0xFF6C63FF)),
            ChartCategory("Transfer", 15f, Color(0xFF64B5F6)),
            ChartCategory("Food", 10f, Color(0xFFFF7043)),
            ChartCategory("Shopping", 12f, Color(0xFFBA68C8)),
            ChartCategory("Car", 8f, Color(0xFF81C784))
        )
    }

    fun updateQuery(q: String) {
        query = q
    }

    fun searchResults(): List<Transaction> {
        if (query.isBlank()) return transactions
        val q = query.trim().lowercase()
        return transactions.filter {
            it.name.lowercase().contains(q) || it.category.lowercase().contains(q)
        }
    }
}
