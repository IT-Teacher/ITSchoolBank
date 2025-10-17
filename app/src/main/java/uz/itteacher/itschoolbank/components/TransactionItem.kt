package uz.itteacher.itschoolbank.components


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.model.Transaction
import androidx.compose.ui.graphics.Color

@SuppressLint("DefaultLocale")
@Composable
fun TransactionItem(tx: Transaction, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = tx.iconRes),
                contentDescription = null,
                modifier = Modifier.size(44.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = tx.name, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(text = tx.category, fontSize = 12.sp, color = Color.Gray)
            }

            val amountText = if (tx.amount >= 0) "+$${String.format("%.2f", tx.amount)}" else "-$${String.format("%.2f", -tx.amount)}"
            val amountColor = if (tx.amount >= 0) Color(0xFF2E7D32) else Color(0xFFD32F2F)
            Text(text = amountText, fontWeight = FontWeight.Bold, color = amountColor)
        }

        Divider(color = Color(0xFFECEFF1), thickness = 0.5.dp)
    }
}
