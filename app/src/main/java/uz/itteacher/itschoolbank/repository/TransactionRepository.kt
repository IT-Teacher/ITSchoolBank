package uz.itteacher.itschoolbank.repository

import uz.itteacher.itschoolbank.R
import uz.itteacher.itschoolbank.model.Transaction

/**
 * Local fake repository for UI development.
 * Later replace implementation with Firestore calls (same API shape).
 */
object TransactionRepository {

    fun getTransactions(): List<Transaction> {
        return listOf(
            Transaction("1", "Apple Store", "Entertainment", -5.99, R.drawable.apple, "Today"),
            Transaction("2", "Spotify", "Music", -12.99, R.drawable.spotify, "Today"),
            Transaction("3", "Money Transfer", "Transaction", 300.0, R.drawable.transfer, "Today"),
            Transaction("4", "Grocery", "Shopping", -88.0, R.drawable.shopping, "Yesterday"),
            Transaction("5", "Fuel", "Car", -35.50, R.drawable.spotify, "02 Oct"),
            Transaction("6", "Salary", "Income", 1200.0, R.drawable.shopping, "01 Oct")
        )
    }
}
