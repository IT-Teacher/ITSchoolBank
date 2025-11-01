package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.itteacher.itschoolbank.R
import uz.itteacher.itschoolbank.viewmodel.MainTransactionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import uz.itteacher.itschoolbank.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMoneyScreen(viewModel: MainTransactionViewModel = viewModel()) {
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val users by viewModel.allUsers.collectAsState()
    val ctx = LocalContext.current

    LaunchedEffect(Unit) { viewModel.reloadUsers { /* no-op */ } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.back), contentDescription = "Back", modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(70.dp))
                Text(text = "Send Money", fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(30.dp))

            Text("Select Receiver", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            if (users.isEmpty()) {
                Text("No other users found", color = Color.Gray)
            } else {
                users.filter { it.id != (viewModel.currentUser.value?.id ?: "") }.take(6).forEach { u ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(if (selectedUser?.id == u.id) Color(0xFFE3F2FD) else Color.Transparent, shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                            .clickable { selectedUser = u },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(painter = painterResource(R.drawable.profile_gray), contentDescription = null, modifier = Modifier.size(44.dp).clip(CircleShape))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(u.name, fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold)
                            Text(u.email, color = Color.Gray, fontSize = 12.sp)
                        }
                        Text("$${"%.2f".format(u.balance)}", fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Enter Amount", color = Color.Gray, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                placeholder = { Text("0.00") },
                textStyle = TextStyle(fontSize = 22.sp),
                modifier = Modifier.fillMaxWidth().height(65.dp),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Description", color = Color.Gray, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Add a note (optional)") },
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    val toUid = selectedUser?.id
                    val amt = amount.toDoubleOrNull() ?: 0.0
                    if (toUid.isNullOrBlank()) { Toast.makeText(ctx, "Select a receiver", Toast.LENGTH_SHORT).show(); return@Button }
                    if (amt <= 0.0) { Toast.makeText(ctx, "Enter valid amount", Toast.LENGTH_SHORT).show(); return@Button }
                    viewModel.sendMoney(toUid, amt, description, "transfer", "transfer") { success, err ->
                        if (success) {
                            Toast.makeText(ctx, "Sent $${"%.2f".format(amt)}", Toast.LENGTH_SHORT).show()
                            amount = ""
                            description = ""
                            selectedUser = null
                        } else {
                            Toast.makeText(ctx, "Error: ${err ?: "unknown"}", Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
            ) {
                Text(text = "Send", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Make sure the receiver’s information is correct before sending.", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.fillMaxWidth().padding(8.dp))
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
