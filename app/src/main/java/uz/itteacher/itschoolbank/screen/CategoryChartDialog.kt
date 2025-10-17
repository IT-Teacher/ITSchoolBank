package uz.itteacher.itschoolbank.screen


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uz.itteacher.itschoolbank.viewmodel.ChartCategory
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.Color

@Composable
fun CategoryChartDialog(categories: List<ChartCategory>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Category Statistics") },
        text = {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(220.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.size(180.dp)) {
                        val totalAngle = 360f
                        var startAngle = -90f
                        val total = categories.sumOf { it.percent.toDouble() }.toFloat().takeIf { it > 0f } ?: 100f
                        for (cat in categories) {
                            val sweep = (cat.percent / total) * totalAngle
                            drawArc(color = cat.color, startAngle = startAngle, sweepAngle = sweep, useCenter = false, style = Stroke(width = 24f))
                            startAngle += sweep
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column {
                    for (cat in categories) {
                        Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(12.dp).background(cat.color))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("${cat.name} — ${cat.percent}%")
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Close") }
        }
    )
}
