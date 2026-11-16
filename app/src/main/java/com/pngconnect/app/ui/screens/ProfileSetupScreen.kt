package com.pngconnect.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pngconnect.app.ui.theme.PngGoldPrimary
import com.pngconnect.app.ui.theme.PngRedPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetupScreen(onProfileCompleted: () -> Unit) {
    var nickname by remember { mutableStateOf("") }
    var birthYear by remember { mutableStateOf("") }
    var selectedProvince by remember { mutableStateOf("National Capital District") }
    var agreedToTerms by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val currentYear = 2026

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Setup", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F1015))
            )
        },
        containerColor = Color(0xFF0F1015)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Welcome to PNG Connect",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Set up your profile to connect with fellow adults across Papua New Guinea.",
                fontSize = 14.sp,
                color = Color(0xFFA0A5B5)
            )

            // Nickname Field
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Nickname / Wantok Name") },
                placeholder = { Text("e.g. Kila, Bari, Junior") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Age Verification
            OutlinedTextField(
                value = birthYear,
                onValueChange = { birthYear = it.take(4) },
                label = { Text("Birth Year (Must be 18+)") },
                placeholder = { Text("e.g. 2002") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    Text("Users must be at least 18 years old to use PNG Connect.")
                }
            )

            // Province Selection
            OutlinedTextField(
                value = selectedProvince,
                onValueChange = { selectedProvince = it },
                label = { Text("Province (Optional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            // Age verification checkbox
            Row(modifier = Modifier.fillMaxWidth()) {
                Checkbox(
                    checked = agreedToTerms,
                    onCheckedChange = { agreedToTerms = it },
                    colors = CheckboxDefaults.colors(checkedColor = PngGoldPrimary)
                )
                Text(
                    text = "I confirm that I am 18 years or older and agree to follow PNG community safety guidelines.",
                    fontSize = 13.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }

            Button(
                onClick = {
                    val year = birthYear.toIntOrNull()
                    if (nickname.isBlank()) {
                        errorMessage = "Please enter a nickname."
                    } else if (year == null || (currentYear - year) < 18) {
                        errorMessage = "You must be at least 18 years old to enter PNG Connect."
                    } else if (!agreedToTerms) {
                        errorMessage = "Please verify your age and accept community guidelines."
                    } else {
                        errorMessage = null
                        onProfileCompleted()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PngRedPrimary),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Complete Profile & Enter", fontWeight = FontWeight.Bold)
            }
        }
    }
}
