package com.pngconnect.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pngconnect.app.ui.theme.PngGoldPrimary
import com.pngconnect.app.ui.theme.PngRedPrimary

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0F1015),
                        Color(0xFF1E0E12),
                        Color(0xFF0B0C10)
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Brand Header
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Kumul Bird & 18+ indicator
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(PngRedPrimary, PngGoldPrimary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "PNG",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "PNG Connect",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Wanbel · Stori · Bungim",
                    fontSize = 14.sp,
                    color = PngGoldPrimary,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "The safe video chat platform connecting adults (18+) across all 22 provinces in Papua New Guinea.",
                    fontSize = 15.sp,
                    color = Color(0xFFA0A5B5),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // Key Highlights (Low Data, 18+ Only, Real Connection)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF1C1E26))
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "✓ Strict 18+ Age Verification Required",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "✓ Low Data Saver mode for 2G / 3G Digicel & Vodafone",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "✓ One-tap instant Report & Block for safe conversations",
                    fontSize = 13.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }

            // CTA Button
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PngRedPrimary),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Get Started (18+)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
