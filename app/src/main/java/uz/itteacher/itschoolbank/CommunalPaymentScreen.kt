package uz.itteacher.itschoolbank

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CommunalService(val name: String, val icon: String)

data class City(val name: String)

@Composable
fun CommunalPaymentApp() {
    var currentScreen by remember { mutableStateOf("services") }
    var selectedService by remember { mutableStateOf<CommunalService?>(null) }

    when (currentScreen) {
        "services" -> ServiceSelectionScreen(
            onServiceSelected = { service ->
                selectedService = service
                currentScreen = "payment"
            }
        )
        "payment" -> PaymentScreen(
            service = selectedService,
            onBack = { currentScreen = "services" }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceSelectionScreen(onServiceSelected: (CommunalService) -> Unit) {
    val services = listOf(
        CommunalService("Electricity", "⚡️"),
        CommunalService("Water", "💧"),
        CommunalService("Gas", "🔥"),
        CommunalService("Internet", "🌐")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Service") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(services) { service ->
                ServiceCard(
                    service = service,
                    onClick = { onServiceSelected(service) }
                )
            }
        }
    }
}

@Composable
fun ServiceCard(service: CommunalService, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = service.icon,
                    fontSize = 48.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = service.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(service: CommunalService?, onBack: () -> Unit) {
    if (service == null) {
        // Fallback if no service selected
        Text("No service selected")
        return
    }

    val cities = listOf(
        City("Tashkent"),
        City("Samarkand"),
        City("Bukhara"),
        City("Andijan"),
        City("Namangan"),
        City("Fergana"),
        City("Kashkadarya"),
        City("Syrdarya"),
        City("Jizzakh"),
        City("Navoi"),
        City("Bukhara Region"),
        City("Surkhandarya")
    )

    var selectedCity by remember { mutableStateOf<City?>(null) }
    var homeNumber by remember { mutableStateOf("") }
    var accountNumber by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment for ${service.name}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Select City",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cities) { city ->
                    CityItem(
                        city = city,
                        isSelected = city == selectedCity,
                        onSelect = { selectedCity = city }
                    )
                }
            }

            Text(
                text = "Enter Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = homeNumber,
                onValueChange = { homeNumber = it.filter { char -> char.isDigit() } },
                label = { Text("Home Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = accountNumber,
                onValueChange = { accountNumber = it },
                label = { Text("Account Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    if (it.matches(Regex("^\\d*\\.?\\d{0,2}$"))) {
                        amount = it
                    }
                },
                label = { Text("Amount (UZS)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = {
                    if (selectedCity == null) {
                        errorMessage = "Please select a city"
                    } else if (homeNumber.isEmpty()) {
                        errorMessage = "Please enter home number"
                    } else if (accountNumber.isEmpty()) {
                        errorMessage = "Please enter account number"
                    } else if (amount.isEmpty() || amount.toDoubleOrNull() == null || amount.toDouble() <= 0) {
                        errorMessage = "Please enter a valid amount"
                    } else {
                        errorMessage = null
                        showConfirmation = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Proceed to Payment")
            }

            if (showConfirmation) {
                AlertDialog(
                    onDismissRequest = { showConfirmation = false },
                    title = { Text("Confirm Payment") },
                    text = {
                        Column {
                            Text("Service: ${service.name}")
                            Text("City: ${selectedCity!!.name}")
                            Text("Home Number: $homeNumber")
                            Text("Account Number: $accountNumber")
                            Text("Amount: ${String.format("%.2f", amount.toDouble())} UZS")
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            showConfirmation = false
                            selectedCity = null
                            homeNumber = ""
                            accountNumber = ""
                            amount = ""
                        }) {
                            Text("Confirm Payment")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showConfirmation = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CityItem(
    city: City,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surface
        ),
        onClick = onSelect
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = city.name,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}