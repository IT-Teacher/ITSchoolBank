package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uz.itteacher.itschoolbank.viewmodel.PinViewModel

@Composable
fun CreatePinScreen(
    viewModel: PinViewModel,
    onPinCreated: () -> Unit
) {
    var enteredPin by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pin Code",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Create Pin Code",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(4) { index ->
                val filled = index < enteredPin.length
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(if (filled) Color.Black else Color.LightGray)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        NumberPad(
            onNumberClick = {
                if (enteredPin.length < 4) enteredPin += it
            },
            onDeleteClick = {
                if (enteredPin.isNotEmpty()) enteredPin = enteredPin.dropLast(1)
            },
            onOkClick = {
                if (enteredPin.length == 4) {
                    viewModel.createPin(enteredPin)
                    onPinCreated()
                }
            }
        )
    }
}
