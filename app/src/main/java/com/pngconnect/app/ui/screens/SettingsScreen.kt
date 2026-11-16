package com.pngconnect.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pngconnect.app.ui.theme.PngGoldPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onOpenSafety: () -> Unit
) {
    var dataSaver by remember { mutableStateOf(true) }
    var hideProvince by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F1015))
            )
        },
        containerColor = Color(0xFF0F1015)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Data & Performance", fontWeight = FontWeight.Bold, color = PngGoldPrimary)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text("PNG Data Saver", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Reduces video bitrates to save mobile data on Digicel / Vodafone PNG.", fontSize = 12.sp, color = Color(0xFFA0A5B5))
                }
                Switch(checked = dataSaver, onCheckedChange = { dataSaver = it })
            }

            Divider(color = Color(0xFF22252D))

            Text("Privacy & Safety", fontWeight = FontWeight.Bold, color = PngGoldPrimary)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                    Text("Hide Province in Calls", fontWeight = FontWeight.SemiBold, color = Color.White)
                    Text("Keep your location private during chat matches.", fontSize = 12.sp, color = Color(0xFFA0A5B5))
                }
                Switch(checked = hideProvince, onCheckedChange = { hideProvince = it })
            }

            OutlinedButton(
                onClick = onOpenSafety,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Open Community Safety & Guidelines", color = Color.White)
            }
        }
    }
}
