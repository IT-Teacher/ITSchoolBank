package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.itteacher.itschoolbank.components.TransactionItem
import uz.itteacher.itschoolbank.viewmodel.TransactionViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun TransactionHistoryScreen(viewModel: TransactionViewModel = viewModel(), onSeeAll: () -> Unit = {}) {
    val transactions = viewModel.transactions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Transaction History", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = onSeeAll) { Text("See All") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = transactions, key = { it.id }) { tx ->
                TransactionItem(tx)
            }
        }
    }
}
