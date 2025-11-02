package uz.itteacher.itschoolbank.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.R
import uz.itteacher.itschoolbank.modul.BankCard
import uz.itteacher.itschoolbank.viewmodel.CardViewModel

@Composable
fun CardDashboard(viewModel: CardViewModel) {
    var selectedIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadCards()
    }

    Scaffold(
        bottomBar = {
            FixedBottomNavigationRow(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Search Row
            item {
                var searchText by remember { mutableStateOf("") }
                SearchRow(
                    firstName = "Tanya Myroniuk",
                    imageResId = R.drawable.pfp,
                    searchQuery = searchText,
                    onSearchQueryChange = { searchText = it },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Cards Row
            item {
                CardBox(cards = viewModel.cards)
            }

            // Four Action Buttons
            item {
                FourImageButtonsRow { index ->
                    when (index) {
                        0 -> println("Top Up")
                        1 -> println("Send Money")
                        2 -> println("Loan")
                        3 -> println("Receive")
                    }
                }
            }

            // Transactions
            item {
                TransactionScreen()
            }
        }
    }
}

@Composable
fun SearchRow(
    firstName: String,
    imageResId: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = firstName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .width(180.dp)
                .height(56.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray
            ),
            singleLine = true
        )
    }
}

@Composable
fun CardBox(cards: List<BankCard>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        items(cards) { card ->
            CardItem(card)
        }
    }
}

@Composable
fun CardItem(card: BankCard) {
    Row(modifier = Modifier.padding(start = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Box(
            modifier = Modifier
                .width(340.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF1E1E2F))


        ) {
        Image(
            painter = painterResource(R.drawable.worldmap),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(painterResource(R.drawable.sim_card), "Chip", Modifier.size(36.dp))
                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        listOf(R.drawable.union, R.drawable.union3, R.drawable.union2, R.drawable.union1).forEach {
                            Image(painterResource(it), null, Modifier.size( width = if (it == R.drawable.union)  3.dp else if (it == R.drawable.union3) 4.dp else if (it == R.drawable.union2) 5.dp else 6.dp, height = if (it == R.drawable.union)  9.dp else if (it == R.drawable.union3) 13.dp else if (it == R.drawable.union2) 18.dp else 21.dp))
                            Spacer(Modifier.width(1.dp))
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))
                Text(
                    text = card.cardNumber.replaceRange(4, 12, " **** **** "),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Expiry Date", color = Color.White.copy(0.6f), fontSize = 11.sp)
                        Text(card.expiryDate, color = Color.White, fontSize = 13.sp)
                    }
                    Column {
                        Text("CVV", color = Color.White.copy(0.6f), fontSize = 11.sp)
                        Text(card.cvv, color = Color.White, fontSize = 13.sp)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(card.cardholderName, color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
                    Image(
                        painter = painterResource(
                            when (card.cardType) {
                                "Mastercard" -> R.drawable.mastercard
                                "Visa" -> R.drawable.ic_visa
                                "American Express" -> R.drawable.ic_amex
                                "UzCard" -> R.drawable.ic_uzcard
                                else -> R.drawable.mastercard
                            }
                        ),
                        contentDescription = card.cardType,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FourImageButtonsRow(onButtonClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ImageCircleButton(R.drawable.up, { onButtonClick(0) })
        ImageCircleButton(R.drawable.down, { onButtonClick(1) })
        ImageCircleButton(R.drawable.loan, { onButtonClick(2) })
        ImageCircleButton(R.drawable.topup, { onButtonClick(3) })
    }
}

@Composable
fun ImageCircleButton(imageRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Color(0xFFF4F4F4))
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(56.dp).clip(CircleShape)
        )
    }
}

val appleIcon = R.drawable.apple
val spotifyIcon = R.drawable.spotify
val transferIcon = R.drawable.transfer_down

@Composable
fun TransactionScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(vertical = 16.dp)
        ) {
            Text(
                "Transaction",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                "Sell All",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1976D2),
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }

        TransactionItem(appleIcon, "Apple Store", "Entertainment", "- $5.99", Color.Gray)
        Spacer(Modifier.height(16.dp))
        TransactionItem(spotifyIcon, "Spotify", "Music", "- $12.99", Color.Gray)
        Spacer(Modifier.height(16.dp))
        TransactionItem(transferIcon, "Money Transfer", "Transaction", "$300", Color(0xFF1976D2))
    }
}

@Composable
fun TransactionItem(iconRes: Int, title: String, subtitle: String, amount: String, amountColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color(0xFFF5F5F5))
                .padding(10.dp)
                .size(42.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
            Text(subtitle, fontSize = 14.sp, color = Color.Gray)
        }
        Text(amount, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = amountColor)
    }
}

// MARK: - Bottom Navigation
@Composable
fun FixedBottomNavigationRow(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F7FA))
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(R.drawable.home, "Home", selectedIndex == 0) { onItemSelected(0) }
        BottomNavItem(R.drawable.wallet, "My Cards", selectedIndex == 1) { onItemSelected(1) }
        BottomNavItem(R.drawable.statistics, "Statistics", selectedIndex == 2) { onItemSelected(2) }
        BottomNavItem(R.drawable.settings, "Settings", selectedIndex == 3) { onItemSelected(3) }
    }
}

@Composable
fun BottomNavItem(iconRes: Int, label: String, isSelected: Boolean, onClick: () -> Unit) {
    val tint = if (isSelected) Color(0xFF1976D2) else Color(0xFF90A4AE)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(painterResource(iconRes), label, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(Modifier.height(4.dp))
        Text(label, color = tint, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal)
    }
}

// MARK: - Preview
@Preview(showBackground = true)
@Composable
fun CardDashboardPreview() {
    CardDashboard(CardViewModel())
}