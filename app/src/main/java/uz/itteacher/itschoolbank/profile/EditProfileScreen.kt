package uz.itteacher.itschoolbank.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.R

@Composable
fun EditProfileScreen(){
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "back",
                    Modifier.size(20.dp)
                )
            }

            Text("Edit Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        }
        Column(
            modifier = Modifier
                .padding(20.dp, 0.dp),
        ) {
            Image(
                painter = painterResource(R.drawable.woman),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .clickable { },
                contentScale = ContentScale.Crop
            )

            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Tanya Myroniuk", fontSize = 20.sp)
                Text(text = "Senior Designer", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
        TextField(value = name, onValueChange = {name = it},
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "leading icon",
                    tint = Color.Gray)
            }
        )
        TextField(value = email, onValueChange = {email = it},
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.email),
                    contentDescription = "leading icon",
                    tint = Color.Gray)
            }
        )
        TextField(value = phone, onValueChange = {phone = it},
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.telephone),
                    contentDescription = "leading icon",
                    tint = Color.Gray)
            }
        )
    }

}
