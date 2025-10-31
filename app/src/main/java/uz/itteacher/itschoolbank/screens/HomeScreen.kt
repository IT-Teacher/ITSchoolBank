package uz.itteacher.itschoolbank.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.R
import uz.itteacher.itschoolbank.modul.BankCard
import uz.itteacher.itschoolbank.viewmodel.CardViewModel

@Composable
fun CardDashboard(viewModel: CardViewModel){
    viewModel.loadCards()
    val cards = viewModel.cards
    Column {

        var searchText by remember { mutableStateOf("") }

        SearchRow("Tanya Myroniuk",
            R.drawable.pfp,
            searchText,
            onSearchQueryChange = { searchText = it },
            )

        CardBox(cards)

    }
}

@Composable
fun SearchRow(
    firstName: String,
    imageResId: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
//                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = firstName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1.5f)
        ) {
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
                    .fillMaxWidth()
                    .height(56.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = Color.Gray
                ),
                singleLine = true,
                maxLines = 1
            )
        }
    }
}

@Composable
fun CardBox(cards : List<BankCard>){


    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
    ) {
        items(cards) { card ->
            CardItem(card)
        }
    }

}

@Composable
fun CardItem(card: BankCard) {
    Box(
        modifier = Modifier
            .height(298.dp)
            .width(400.dp)
            .padding(8.dp)
            .background(Color(0xFF1E1E2F), RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sim_card),
                    contentDescription = "Chip",
                    modifier = Modifier.size(40.dp)
                )
                Row(modifier = Modifier.size(4.17.dp, 21.05.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.union),
                        contentDescription = "Contactless",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.union3),
                        contentDescription = "Contactless",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))

                    Image(
                        painter = painterResource(id = R.drawable.union2),
                        contentDescription = "Contactless",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(id = R.drawable.union1),
                        contentDescription = "Contactless",
                        modifier = Modifier.size(20.dp)
                    )
                }
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
                        "Mastercard" -> R.drawable.mastercard
                        "Visa" -> R.drawable.ic_visa
                        "American Express" -> R.drawable.ic_amex
                        "UzCard" -> R.drawable.ic_uzcard
                        else -> R.drawable.mastercard
                    }),
                    contentDescription = card.cardType,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

