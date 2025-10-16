package uz.itteacher.itschoolbank

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EditCardScreen(viewModel: CardViewModel, card: BankCard, onBack: () -> Unit) {
    var cardholderName by remember { mutableStateOf(card.cardholderName) }
    var expiryDate by remember { mutableStateOf(card.expiryDate) }
    var cvv by remember { mutableStateOf(card.cvv) }
    var cardNumber by remember { mutableStateOf(card.cardNumber) }
    var cardType by remember { mutableStateOf(card.cardType) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onBack) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_revert),
                contentDescription = "Back"
            )
        }
        Text("Edit Card", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        CardItem(card) {}

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = cardholderName,
            onValueChange = { cardholderName = it },
            label = { Text("Cardholder Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = expiryDate,
            onValueChange = { expiryDate = it },
            label = { Text("Expiry Date (MM/YY)") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cvv,
            onValueChange = { cvv = it },
            label = { Text("4-digit CVV") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { newValue ->
                val digitsOnly = newValue.replace("[^0-9]".toRegex(), "")
                val limitedDigits = if (digitsOnly.length > 16) digitsOnly.substring(0, 16) else digitsOnly
                cardNumber = buildString {
                    limitedDigits.forEachIndexed { index, char ->
                        append(char)
                        if ((index + 1) % 4 == 0 && index < 15) append(" ")
                    }
                }
            },
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage != null
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))

        Box {
            OutlinedTextField(
                value = cardType,
                onValueChange = { },
                label = { Text("Card Type") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = android.R.drawable.arrow_down_float),
                        contentDescription = "Dropdown",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        }
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuItem(
                    text = { Text("Mastercard") },
                    onClick = { cardType = "Mastercard"; expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("Visa") },
                    onClick = { cardType = "Visa"; expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("American Express") },
                    onClick = { cardType = "American Express"; expanded = false }
                )
                DropdownMenuItem(
                    text = { Text("UzCard") },
                    onClick = { cardType = "UzCard"; expanded = false }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty() || cardholderName.isEmpty()) {
                    errorMessage = "All fields are required"
                    return@Button
                }
                if (cardNumber.replace(" ", "").length != 16) {
                    errorMessage = "Card number must be 16 digits"
                    return@Button
                }
                if (!expiryDate.matches("\\d{2}/\\d{2}".toRegex())) {
                    errorMessage = "Invalid expiry date (MM/YY)"
                    return@Button
                }
                if (cvv.length !in 3..4) {
                    errorMessage = "CVV must be 3 or 4 digits"
                    return@Button
                }

                val updatedCard = card.copy(
                    cardNumber = cardNumber,
                    cardholderName = cardholderName,
                    expiryDate = expiryDate,
                    cvv = cvv,
                    cardType = cardType
                )
                viewModel.updateCard(updatedCard)
                errorMessage = null
                onBack()
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}