package uz.itteacher.itschoolbank.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uz.itteacher.itschoolbank.R

@Composable
fun SettingsScreen(navController: NavController) {
    val selectedLanguage = remember { mutableStateOf("English") }

    navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<String>("selectedLanguage")
        ?.observeForever { language ->
            selectedLanguage.value = language
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { /* Add back navigation if needed */ }) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Text(
                text = "Settings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        SectionHeader("General")
        SettingsItem("Language", selectedLanguage.value) {
            navController.navigate("language")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)
        SettingsItem("My Profile") {
            navController.navigate("profile")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)
        SettingsItem("Contact Us")

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)
        SectionHeader("Security")
        SettingsItem("Change Password") {
            navController.navigate("change")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)
        SettingsItem("Privacy Policy") {
            navController.navigate("term")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.Gray)

        Text(
            text = "Choose what data you share with us",
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Biometric", fontSize = 16.sp)
            var checked by remember { mutableStateOf(false) }
            Switch(checked = checked, onCheckedChange = { checked = it })
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontSize = 14.sp,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
fun SettingsItem(title: String, subtitle: String? = null, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp)
        Row {
            if (subtitle != null) {
                Text(text = subtitle, color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(Modifier.width(10.dp))
            Icon(
                painter = painterResource(R.drawable.next),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
