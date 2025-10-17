package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.itteacher.itschoolbank.components.TransactionItem
import uz.itteacher.itschoolbank.viewmodel.TransactionViewModel
import androidx.compose.ui.graphics.Color

@Composable
fun SearchScreen(viewModel: TransactionViewModel = viewModel()) {
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                viewModel.updateQuery(it)
            },
            label = { Text("Search transactions") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        val results = viewModel.searchResults()

        if (results.isEmpty()) {
            Text("No results", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn {
                items(results, key = { it.id }) { tx ->
                    TransactionItem(tx)
                }
            }
        }
    }
}
