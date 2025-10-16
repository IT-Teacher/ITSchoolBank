package uz.itteacher.itschoolbank.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uz.itteacher.itschoolbank.R

@Composable
fun EditProfileScreen(navController: NavController) {
    var fullName by remember { mutableStateOf(TextFieldValue("Tanya Myroniuk")) }
    var email by remember { mutableStateOf(TextFieldValue("tanya.myroniuk@gmail.com")) }
    var phone by remember { mutableStateOf(TextFieldValue("+8801712663389")) }
    var day by remember { mutableStateOf("28") }
    var month by remember { mutableStateOf("September") }
    var year by remember { mutableStateOf("2000") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f), CircleShape)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp).clickable{navController.popBackStack()}
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Edit Profile",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.woman),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .clickable { },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Tanya Myroniuk", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Text(text = "Senior Designer", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = { Text("Full Name") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "User",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Email Address") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.email),
                    contentDescription = "Email",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        TextField(
            value = phone,
            onValueChange = { phone = it },
            placeholder = { Text("Phone Number") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.telephone),
                    contentDescription = "Phone",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column {
            Text(
                text = "Birth Date",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = day,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { }
                )
                Text(
                    text = month,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { }
                )
                Text(
                    text = year,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { }
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Joined 28 Jan 2021",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
