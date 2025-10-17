package uz.itteacher.itschoolbank.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.viewmodel.PinViewModel

@Composable
fun EnterPinScreen(
    viewModel: PinViewModel,
    onAccessGranted: () -> Unit,
    onBlocked: () -> Unit
) {
    val state = viewModel.state
    var enteredPin by remember { mutableStateOf("") }
    val remainingChances = 3 - state.failedAttempts


    if (state.blockEndTime != null && viewModel.remainingBlockSeconds() > 0) {
        onBlocked()
    }


    if (state.accessGranted) {
        onAccessGranted()
    }


    val targetColor = if (state.failedAttempts > 0) Color.Red else Color.Gray
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 500)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pin Code",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter Pin Code",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            color = if (index < enteredPin.length) Color.Black else Color.LightGray,
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "You have $remainingChances chances left",
            color = animatedColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))


        val digits = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("←", "0", "OK")
        )

        digits.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    Button(
                        onClick = {
                            when (key) {
                                "←" -> if (enteredPin.isNotEmpty())
                                    enteredPin = enteredPin.dropLast(1)
                                "OK" -> {
                                    if (enteredPin.length == 4) {
                                        viewModel.tryPin(enteredPin)
                                        enteredPin = ""
                                    }
                                }
                                else -> if (enteredPin.length < 4)
                                    enteredPin += key
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE0E0E0),
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.size(80.dp)
                    ) {
                        Text(text = key, fontSize = 20.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
