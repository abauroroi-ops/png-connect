
package com.pngconnect.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pngconnect.app.SupabaseManager
import com.pngconnect.app.ui.theme.PngGoldPrimary
import com.pngconnect.app.ui.theme.PngRedPrimary
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onStartChat: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenSafety: () -> Unit
) {
    var connectionStatus by remember { mutableStateOf("Checking Supabase…") }
    var isChecking by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    fun checkConnection() {
        if (isChecking) return

        scope.launch {
            isChecking = true
            connectionStatus = "Checking Supabase…"

            val connected = SupabaseManager().testConnection()

            connectionStatus = if (connected) {
                "Supabase connection successful"
            } else {
                "Supabase not reachable. Check internet or project URL."
            }

            isChecking = false
        }
    }

    LaunchedEffect(Unit) {
        checkConnection()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                checkConnection()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Scaffold(
        containerColor = Color(0xFF0F1015),
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF161820)) {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = { Icon(Icons.Default.Videocam, "Home") },
                    label = { Text("Chat") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenSafety,
                    icon = { Icon(Icons.Default.Security, "Safety") },
                    label = { Text("Safety") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenProfile,
                    icon = { Icon(Icons.Default.Person, "Profile") },
                    label = { Text("Profile") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenSettings,
                    icon = { Icon(Icons.Default.Settings, "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "PNG Connect",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "1,420 adults online across PNG",
                        fontSize = 12.sp,
                        color = PngGoldPrimary
                    )
                }

                IconButton(onClick = onOpenSafety) {
                    Icon(Icons.Default.Security, "Safety Hub", tint = Color.White)
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = onStartChat,
                    modifier = Modifier
                        .size(170.dp)
                        .clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PngRedPrimary
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = "Start Video Chat",
                            tint = Color.White,
                            modifier = Modifier.size(54.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "START CHAT",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Tap to match with a friendly peer in PNG",
                    fontSize = 14.sp,
                    color = Color(0xFFA0A5B5)
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E212B)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Backend Connection",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = connectionStatus,
                            fontSize = 12.sp,
                            color = when {
                                isChecking -> PngGoldPrimary
                                connectionStatus == "Supabase connection successful" ->
                                    Color(0xFF10B981)
                                else -> Color(0xFFFF8A80)
                            }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        TextButton(
                            onClick = { checkConnection() },
                            enabled = !isChecking
                        ) {
                            Text(if (isChecking) "Checking…" else "Check again")
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E212B)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF10B981))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "PNG Low Data Optimization Active",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Designed for mobile networks in PNG",
                                fontSize = 11.sp,
                                color = Color(0xFFA0A5B5)
                            )
                        }
                    }
                }
            }
        }
    }
}
