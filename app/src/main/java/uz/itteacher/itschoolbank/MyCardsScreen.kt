package uz.itteacher.itschoolbank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyCardsScreen(viewModel: CardViewModel, onAddCard: () -> Unit, onCardClick: () -> Unit) {
    val cards = viewModel.cards

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { /* Handle back if needed */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_revert),
                    contentDescription = "Back"
                )
            }
            Text("My Cards", style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = onAddCard) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_add),
                    contentDescription = "Add Card"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (cards.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_empty), // Custom empty icon
                    contentDescription = "No cards",
                    modifier = Modifier.size(600.dp) // Increased size to 150 dp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No cards added yet",
                    modifier = Modifier.padding(top = 250.dp), // Space between image and text
                    fontSize = 30.sp, // Larger font size
                    fontWeight = FontWeight.Bold, // Bold styling
                    color = Color.Gray // Stylish color
                )
            }
        } else {
            LazyColumn {
                items(listOf(cards.last())) { card ->
                    CardItem(card) {
                        onCardClick()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    card.transactions.take(3).forEach { transaction ->
                        TransactionItem(transaction)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cards.isNotEmpty()) {
            Text("Monthly spending limit", style = MaterialTheme.typography.titleMedium)
            Slider(
                value = cards.last().monthlyLimit,
                onValueChange = { /* Update logic can be added here */ },
                valueRange = 0f..10000f,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Blue,
                    activeTrackColor = Color.Blue
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Amount: $${cards.last().spentAmount}", fontSize = 16.sp)
                Text(text = "$${cards.last().monthlyLimit}", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CardItem(card: BankCard, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF1E1E2F), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_chip),
                    contentDescription = "Chip",
                    modifier = Modifier.size(40.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_contactless),
                    contentDescription = "Contactless",
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = card.cardNumber.replaceRange(4, 12, " **** **** "),
                color = Color.White,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Expiry Date", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    Text(text = card.expiryDate, color = Color.White, fontSize = 14.sp)
                }
                Column {
                    Text(text = "CVV", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    Text(text = card.cvv, color = Color.White, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = card.cardholderName, color = Color.White, fontSize = 16.sp)
                Image(
                    painter = painterResource(id = when (card.cardType) {
                        "Mastercard" -> R.drawable.ic_mastercard
                        "Visa" -> R.drawable.ic_visa
                        "American Express" -> R.drawable.ic_amex
                        "UzCard" -> R.drawable.ic_uzcard
                        else -> R.drawable.ic_mastercard
                    }),
                    contentDescription = card.cardType,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = getIconForCategory(transaction.category)),
                contentDescription = transaction.category,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = transaction.merchant, color = Color.Black, fontSize = 14.sp)
                Text(text = transaction.category, color = Color.Gray, fontSize = 12.sp)
            }
        }
        Text(text = "- $${transaction.amount}", color = Color.Black, fontSize = 14.sp)
    }
}

private fun getIconForCategory(category: String): Int {
    return when (category.lowercase()) {
        "entertainment" -> android.R.drawable.ic_dialog_info
        "music" -> android.R.drawable.ic_dialog_email
        "shopping" -> android.R.drawable.ic_dialog_alert
        else -> android.R.drawable.ic_dialog_info
    }
}