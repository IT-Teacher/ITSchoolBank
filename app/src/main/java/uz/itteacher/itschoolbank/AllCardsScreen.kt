package uz.itteacher.itschoolbank

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AllCardsScreen(
    viewModel: CardViewModel,
    navController: NavController,
    onBack: () -> Unit,
    onEditCard: (BankCard) -> Unit // Added parameter for editing
) {
    val cards = viewModel.cards.drop(1)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_revert),
                    contentDescription = "Back"
                )
            }
            Text("All Cards", style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = { /* No action */ }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_add),
                    contentDescription = "Add Card"
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(cards) { card ->
                CardItem(card) {
                    viewModel.setMainCard(card)
                    navController.navigate("myCards") {
                        popUpTo("myCards") { inclusive = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        onEditCard(card) // Call the onEditCard lambda
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("Edit this Card")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}