package com.pngconnect.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pngconnect.app.ui.theme.PngGoldPrimary
import com.pngconnect.app.ui.theme.PngRedPrimary

@Composable
fun CallEndedScreen(
    onFindAnother: () -> Unit,
    onReturnHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F1015))
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Call Ended",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tenkyu tumas for having a respectful chat!",
                fontSize = 14.sp,
                color = Color(0xFFA0A5B5)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Call Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E212B)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text("Call Summary", fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Duration: 3m 42s", color = Color(0xFFA0A5B5), fontSize = 13.sp)
                    Text("Estimated Data Used: 8.4 MB (Low Data Mode)", color = PngGoldPrimary, fontSize = 13.sp)
                    Text("Province: Madang", color = Color(0xFFA0A5B5), fontSize = 13.sp)
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onFindAnother,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PngRedPrimary),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Refresh, "Find Another")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Find Another Person", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = onReturnHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.Home, "Home", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Return to Home", color = Color.White)
            }
        }
    }
}
