package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun CategoryChartDialog(categories: List<uz.itteacher.itschoolbank.viewmodel.ChartCategory>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onDismiss() }) { Text("Close") }
        },
        title = { Text("Category Statistics", style = MaterialTheme.typography.titleMedium) },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Canvas(modifier = Modifier.size(180.dp)) {
                    val totalAngle = 360f
                    var startAngle = -90f
                    val total = categories.sumOf { it.percent.toDouble() }.toFloat().takeIf { it > 0f } ?: 100f
                    for (cat in categories) {
                        val sweep = (cat.percent / total) * totalAngle
                        drawArc(color = cat.color, startAngle = startAngle, sweepAngle = sweep, useCenter = false, style = Stroke(width = 30f))
                        startAngle += sweep
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    categories.forEach { cat ->
                        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(14.dp).background(cat.color))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${cat.name} — ${cat.percent.toInt()}%", color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    )
}
