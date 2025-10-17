package uz.itteacher.itschoolbank.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import uz.itteacher.itschoolbank.R
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(R.drawable.profile_gray), contentDescription = null, modifier = Modifier.size(96.dp))
        Spacer(modifier = Modifier.height(12.dp))
        Text("John Doe", fontWeight = FontWeight.SemiBold)
        Text("johndoe@gmail.com", color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Account", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Balance: $1,234.56")
            }
        }
    }
}
