package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.itteacher.itschoolbank.components.TransactionItem
import uz.itteacher.itschoolbank.viewmodel.MainTransactionViewModel

@Composable
fun TransactionHistoryScreen(viewModel: MainTransactionViewModel = viewModel()) {
    val transactions by viewModel.transactions.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Transaction History", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = transactions, key = { it.id }) { tx ->
                TransactionItem(tx)
            }
        }
    }
}
