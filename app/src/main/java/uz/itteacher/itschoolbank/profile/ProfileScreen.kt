package uz.itteacher.itschoolbank.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import java.io.File
import java.io.FileOutputStream
import uz.itteacher.itschoolbank.R


fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, filename: String): String {
    val file = File(context.filesDir, filename)
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    return file.absolutePath
}

//fun loadImagePath(context: Context, filename: String): String? {
//    val file = File(context.filesDir, filename)
//    return if (file.exists()) file.absolutePath else null
//}
//
//fun uriToBitmap(context: Context, uri: Uri): Bitmap {
//    return MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
//}

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    var userName by remember { mutableStateOf("Unknown User") }
    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance().getReference("users")
        val snapshot = database.get().await()
        if (snapshot.exists()) {
            val firstUser = snapshot.children.firstOrNull()
            if (firstUser != null) {
                userName = firstUser.child("name").value?.toString() ?: "Unknown User"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 15.dp)
    ) {
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
                IconButton(onClick = {navController.popBackStack()}) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "back",
                        Modifier.size(20.dp)
                    )
                }
            }

            Text("Profile", fontSize = 20.sp,
                fontWeight = FontWeight.Bold)

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.edit),
                    contentDescription = "edit profile",
                    Modifier.size(25.dp).clickable{navController.navigate("edit")}
                )
            }
        }

        Spacer(Modifier.height(30.dp))

        ProfileHeaderSection(context, userName)

        Spacer(Modifier.height(20.dp))

        ProfileMenuItem(R.drawable.user, "Personal Information")
        ProfileMenuItem(R.drawable.creditcard, "Payment Preferences")
        ProfileMenuItem(R.drawable.editcard, "Banks and Cards")
        ProfileMenuItem(R.drawable.notification, "Notifications", showBadge = true, badgeCount = 2)
        ProfileMenuItem(R.drawable.social, "Message Center")
        ProfileMenuItem(R.drawable.location, "Address")
        ProfileMenuItem(R.drawable.setting, "Settings")
    }
}

@Composable
fun ProfileHeaderSection(context: Context, userName: String) {
    var profileBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val file = File(context.filesDir, "profile.png")
        if (file.exists()) {
            profileBitmap = BitmapFactory.decodeFile(file.absolutePath)
        }
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            profileBitmap = it
            saveImageToInternalStorage(context, it, "profile.png")
        }
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            profileBitmap = bitmap
            saveImageToInternalStorage(context, bitmap, "profile.png")
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = if (profileBitmap != null) BitmapPainter(profileBitmap!!.asImageBitmap())
            else painterResource(R.drawable.woman),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .clickable { showDialog = true },
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(20.dp))

        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = userName, fontSize = 20.sp)
            Text(text = "Senior Designer", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(30.dp))
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Select Image") },
            text = { Text("Choose image from Camera or Gallery") },
            confirmButton = {
                Button(onClick = {
                    cameraLauncher.launch(null)
                    showDialog = false
                }) { Text("Camera") }
            },
            dismissButton = {
                Button(onClick = {
                    galleryLauncher.launch("image/*")
                    showDialog = false
                }) { Text("Gallery") }
            }
        )
    }
}


@Composable
fun ProfileMenuItem(
    iconRes: Int,
    text: String,
    showBadge: Boolean = false,
    badgeCount: Int = 0,
    onClick: () -> Unit = {}
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable{onClick()}){
            Icon(
                painterResource(iconRes),
                contentDescription = "profile",
                tint = Color.Gray,
                modifier = Modifier.size(35.dp).padding(5.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(text)
            if (showBadge) {
                Spacer(Modifier.width(8.dp))
                NotificationBadge(badgeCount)
            }
        }
        Icon(
            painterResource(R.drawable.next),
            contentDescription = "further info",
            modifier = Modifier.size(15.dp),
            tint = Color.Gray
        )
    }
    Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 15.dp))
    Spacer(Modifier.height(20.dp))
}

@Composable
fun NotificationBadge(count: Int) {
    if (count > 0) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .background(Color.Red, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = count.toString(), color = Color.White, fontSize = 10.sp)
        }
    }
}

