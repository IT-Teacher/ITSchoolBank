package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.R

@Composable
fun RequestMoneyScreen() {
    var payerName by remember { mutableStateOf("Tanya Myroniuk") }
    var email by remember { mutableStateOf("tanya.myroniuk@gmail.com") }
    var description by remember { mutableStateOf("Some description") }
    var amount by remember { mutableStateOf("26.00.00") }

    var dueDay by remember { mutableStateOf("28") }
    var dueMonth by remember { mutableStateOf("09") }
    var dueYear by remember { mutableStateOf("2000") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "back",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(70.dp))
                Text(
                    text = "Request Money",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp
                )
            }
            Spacer(Modifier.height(30.dp))

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Payer Name",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.profile_gray),
                        contentDescription = "user",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    BasicTextField(
                        value = payerName,
                        onValueChange = { payerName = it },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Email Address",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.email),
                        contentDescription = "email",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    BasicTextField(
                        value = email,
                        onValueChange = { email = it },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Description",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.profile_gray),
                        contentDescription = "description",
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    BasicTextField(
                        value = description,
                        onValueChange = { description = it },
                        singleLine = true,
                        textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(Modifier.height(24.dp))
            }

            Text(
                text = "Monthly Due By",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    value = dueDay,
                    onValueChange = { if (it.length <= 2) dueDay = it.filter { ch -> ch.isDigit() } },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                    modifier = Modifier.width(50.dp)
                )
                BasicTextField(
                    value = dueMonth,
                    onValueChange = { if (it.length <= 2) dueMonth = it.filter { ch -> ch.isDigit() } },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                    modifier = Modifier.width(50.dp)
                )
                BasicTextField(
                    value = dueYear,
                    onValueChange = { if (it.length <= 4) dueYear = it.filter { ch -> ch.isDigit() } },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 15.sp, color = Color.Black),
                    modifier = Modifier.width(80.dp)
                )
            }

            Spacer(Modifier.height(45.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Enter your amount",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Text(
                    text = "Change currency?",
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "USD",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
                BasicTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 30.sp, color = Color.Black),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.96f)
                    .height(50.dp)
                    .background(color = Color(0xFF1E88E5), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Send Money",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
