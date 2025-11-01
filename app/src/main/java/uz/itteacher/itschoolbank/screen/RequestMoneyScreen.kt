package uz.itteacher.itschoolbank.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.itteacher.itschoolbank.R
import uz.itteacher.itschoolbank.viewmodel.MainTransactionViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext

@Composable
fun RequestMoneyScreen(viewModel: MainTransactionViewModel = viewModel()) {
    var toUser by remember { mutableStateOf<String?>(null) }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    val ctx = LocalContext.current
    val users by viewModel.allUsers.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(painter = painterResource(R.drawable.back), contentDescription = "back", modifier = Modifier.size(40.dp))
                Spacer(modifier = Modifier.width(70.dp))
                Text(text = "Request Money")
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Choose user to request from", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            users.filter { it.id != (viewModel.currentUser.value?.id ?: "") }.take(6).forEach { u ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(if (toUser == u.id) Color(0xFFE3F2FD) else Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .clickable { toUser = u.id },
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.profile_gray), contentDescription = null, modifier = Modifier.size(44.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(u.name)
                        Text(u.email, color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Amount", color = Color.Gray)
            OutlinedTextField(value = amount, onValueChange = { amount = it }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))
            Text("Note", color = Color.Gray)
            OutlinedTextField(value = note, onValueChange = { note = it }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val to = toUser
                val amt = amount.toDoubleOrNull() ?: 0.0
                if (to.isNullOrBlank()) { Toast.makeText(ctx, "Choose a user", Toast.LENGTH_SHORT).show(); return@Button }
                if (amt <= 0.0) { Toast.makeText(ctx, "Enter valid amount", Toast.LENGTH_SHORT).show(); return@Button }
                viewModel.createRequest(to, amt, note) { success, err ->
                    if (success) {
                        Toast.makeText(ctx, "Request sent", Toast.LENGTH_SHORT).show()
                        amount = ""
                        note = ""
                        toUser = null
                    } else {
                        Toast.makeText(ctx, "Error: ${err ?: "unknown"}", Toast.LENGTH_LONG).show()
                    }
                }
            }, modifier = Modifier.fillMaxWidth().height(52.dp)) {
                Text("Send Request")
            }
        }

        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.fillMaxWidth(0.96f).height(50.dp).background(color = Color(0xFF1E88E5), shape = RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                Text(text = "Request Money", color = Color.White)
            }
        }
    }
}
