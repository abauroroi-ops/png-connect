package com.pngconnect.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pngconnect.app.ui.theme.AlertRed
import com.pngconnect.app.ui.theme.PngGoldPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafetyScreen(onBack: () -> Unit) {
    var reportedSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Safety & Guidelines", fontWeight = FontWeight.Bold) },
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
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "PNG Community Safety Standards",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PngGoldPrimary
            )

            Text(
                text = "PNG Connect is strictly for adults (18+) to connect positively. We enforce a zero-tolerance policy against misconduct.",
                color = Color.White,
                fontSize = 13.sp
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B1D26)),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("1. Wanbel & Mutual Respect", fontWeight = FontWeight.Bold, color = Color.White)
                    Text("No harassment, hate speech, or offensive content.", fontSize = 12.sp, color = Color(0xFFA0A5B5))

                    Text("2. Strict 18+ Rule", fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Minors are strictly prohibited. Report any suspected underage users.", fontSize = 12.sp, color = Color(0xFFA0A5B5))

                    Text("3. No Recording", fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Do not capture, screenshot, or record video calls without explicit consent.", fontSize = 12.sp, color = Color(0xFFA0A5B5))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Report or Block Last Peer", fontWeight = FontWeight.Bold, color = AlertRed, fontSize = 16.sp)

            Button(
                onClick = { reportedSubmitted = true },
                colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (reportedSubmitted) "Report Filed - User Blocked" else "Report & Block Recent User")
            }

            Text(
                text = "Emergency PNG Support: 1-Tok Kaunselin Helpim Lain: 7100 5555",
                fontSize = 12.sp,
                color = Color(0xFFA0A5B5)
            )
        }
    }
}
