package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.itteacher.itschoolbank.components.TransactionItem
import uz.itteacher.itschoolbank.components.TransactionCard
import uz.itteacher.itschoolbank.viewmodel.TransactionViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import uz.itteacher.itschoolbank.viewmodel.ChartCategory

@Composable
fun HomeScreen(
    viewModel: TransactionViewModel = viewModel(),
    onSeeAll: () -> Unit = {},
    onShowStats: () -> Unit = {}
) {
    val transactions by remember { derivedStateOf { viewModel.transactions } }
    val categories by remember { derivedStateOf { viewModel.categories } }

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        CategoryChartDialog(categories = categories, onDismiss = { showDialog = false })
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        item {
            Text(text = "Category Chart", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))
            Box(modifier = Modifier
                .height(220.dp)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {

                // Donut chart
                Canvas(modifier = Modifier.size(200.dp)) {
                    val totalAngle = 360f
                    var startAngle = -90f
                    val total = categories.sumOf { it.percent.toDouble() }.toFloat().takeIf { it > 0f } ?: 100f
                    for (cat in categories) {
                        val sweep = (cat.percent / total) * totalAngle
                        drawArc(
                            color = cat.color,
                            startAngle = startAngle,
                            sweepAngle = sweep,
                            useCenter = false,
                            style = Stroke(width = 28f)
                        )
                        startAngle += sweep
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("55%", style = MaterialTheme.typography.titleLarge)
                    Text("Transaction", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Transaction History", style = MaterialTheme.typography.titleMedium)
                TextButton(onClick = { onSeeAll() }) {
                    Text("See All")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // show preview of 3 latest transactions
        item {
            Column {
                transactions.takeLast(3).reversed().forEach { tx ->
                    TransactionItem(tx)
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { showDialog = true }) {
                    Text("Statistics")
                }
                Button(onClick = { /* placeholder for send money */ }) {
                    Text("Send Money")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // optional: a list of cards or summaries
        item {
            TransactionCard(title = "Monthly Spend", subtitle = "Based on last month", amount = "$420", modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}
