package uz.itteacher.itschoolbank.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import uz.itteacher.itschoolbank.R
import java.io.File

@Composable
fun EditProfileScreen(navController: NavController) {
    fun loadProfileImage(context: Context): Bitmap? {
        val file = File(context.filesDir, "profile.png")
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    val context = LocalContext.current
    var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }
    LaunchedEffect(Unit) {
        profileBitmap = loadProfileImage(context)
    }
    val database = FirebaseDatabase.getInstance().getReference("users")

    var fullName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var userId by remember { mutableStateOf<String?>(null) }
    var saving by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    val day = "28"
    val month = "September"
    val year = "2000"
    var phone = "+88991025768"

    LaunchedEffect(Unit) {
        val snapshot = database.get().await()
        if (snapshot.exists()) {
            val firstUser = snapshot.children.firstOrNull()
            if (firstUser != null) {
                userId = firstUser.key
                fullName = TextFieldValue(firstUser.child("name").value?.toString() ?: "")
                email = TextFieldValue(firstUser.child("email").value?.toString() ?: "")
            }
        }
    }

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
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
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
                    painter = if (profileBitmap != null) BitmapPainter(profileBitmap!!.asImageBitmap())
                    else painterResource(R.drawable.woman),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = fullName.text.ifEmpty { "Unknown" }, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                Text(text = email.text.ifEmpty { "No email" }, fontSize = 14.sp, color = Color.Gray)
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
                Text(text = day, fontSize = 16.sp, modifier = Modifier.weight(1f))
                Text(text = month, fontSize = 16.sp, modifier = Modifier.weight(1f))
                Text(text = year, fontSize = 16.sp, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (userId != null) {
                    saving = true
                    val updates = mapOf(
                        "name" to fullName.text,
                        "email" to email.text,
                    )
                    database.child(userId!!).updateChildren(updates).addOnCompleteListener { task ->
                        saving = false
                        message = if (task.isSuccessful) "Changes saved" else "Failed to save changes"
                    }
                }
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A86FF)),
            shape = CircleShape
        ) {
            Text(
                text = if (saving) "Saving..." else "Save Changes",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        message?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = it, color = if (it == "Changes saved") Color.Green else Color.Red)
        }
    }
}
