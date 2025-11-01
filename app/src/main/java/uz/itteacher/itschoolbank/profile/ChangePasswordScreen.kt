package uz.itteacher.itschoolbank.profile

import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import uz.itteacher.itschoolbank.R

@Composable
fun ChangePasswordScreen(navController: NavController) {
    val context = LocalContext.current
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showNewPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row( verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp) ) {
            Box( modifier = Modifier .size(40.dp)
                .background(Color.LightGray.copy(alpha = 0.3f),
                    CircleShape)
                .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center ) {
                Icon( painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp) ) }
            Text( text = "Change Password",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp) )
        }
        Text("Current Password",
            color = Color.Gray, fontSize = 14.sp)
        OutlinedTextField(
            value = currentPassword,
            onValueChange = { currentPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 12.dp),
            leadingIcon = { Icon(Icons.Default.Lock,
                contentDescription = null) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation() )
        Text("New Password", color = Color.Gray, fontSize = 14.sp)
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            modifier = Modifier .fillMaxWidth() .padding(top = 6.dp, bottom = 12.dp),
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = null)
                          },
            trailingIcon = {
                IconButton(onClick = { showNewPassword = !showNewPassword }) {
                    Image( painter =
                        if (showNewPassword) painterResource(R.drawable.visibility)
                        else painterResource(R.drawable.eyebrow),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp) )
                }
                           },
            singleLine = true,
            visualTransformation =
                if (showNewPassword) VisualTransformation.None
                else PasswordVisualTransformation() )
        Text("Confirm New Password", color = Color.Gray, fontSize = 14.sp)
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            modifier = Modifier .fillMaxWidth() .padding(top = 6.dp, bottom = 4.dp),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            singleLine = true, visualTransformation = PasswordVisualTransformation() )
        Text(
            text = "Both Passwords Must Match",
            color = Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.padding(start = 8.dp, bottom = 24.dp)
        )

        Button(
            onClick = {
                val databaseRef = FirebaseDatabase.getInstance().getReference("users")
                databaseRef.get().addOnSuccessListener { snapshot ->
                    val firstUser = snapshot.children.firstOrNull()
                    if (firstUser == null) {
                        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    val savedPassword = firstUser.child("password").value?.toString()

                    when {
                        savedPassword == null -> {
                            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                        }
                        savedPassword != currentPassword -> {
                            Toast.makeText(context, "Incorrect current password", Toast.LENGTH_SHORT).show()
                        }
                        newPassword != confirmPassword -> {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        }
                        newPassword.length < 6 -> {
                            Toast.makeText(context, "Password too short", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            firstUser.ref.child("password").setValue(newPassword)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Password changed successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.popBackStack()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Failed to update password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Change Password")
        }
    }
}


