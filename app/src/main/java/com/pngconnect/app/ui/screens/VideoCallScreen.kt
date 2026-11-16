package com.pngconnect.app.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.view.SurfaceView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.pngconnect.app.AgoraManager
import com.pngconnect.app.ui.theme.AlertRed
import com.pngconnect.app.ui.theme.PngGoldPrimary

@Composable
fun VideoCallScreen(
    onEndCall: () -> Unit,
    onOpenSafety: () -> Unit
) {
    val context = LocalContext.current

    var isMuted by remember { mutableStateOf(false) }
    var isCameraOff by remember { mutableStateOf(false) }
    var permissionGranted by remember { mutableStateOf(false) }
    var callMessage by remember { mutableStateOf("Preparing video call…") }
    var remoteUid by remember { mutableStateOf<Int?>(null) }

    val agoraManager = remember { AgoraManager(context) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissionGranted =
            permissions[Manifest.permission.CAMERA] == true &&
            permissions[Manifest.permission.RECORD_AUDIO] == true

        callMessage = if (permissionGranted) {
            "Permissions granted. Ready to connect."
        } else {
            "Camera and microphone permissions are required."
        }
    }

    DisposableEffect(agoraManager) {
        val cameraGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val microphoneGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        permissionGranted = cameraGranted && microphoneGranted

        if (!permissionGranted) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO
                )
            )
        } else {
            callMessage = "Permissions granted. Ready to connect."
        }

        agoraManager.onRemoteUserJoined = { uid ->
            remoteUid = uid
            callMessage = "Connected"
        }

        agoraManager.onRemoteUserLeft = {
            remoteUid = null
            callMessage = "The other person left."
        }

        onDispose {
            agoraManager.onRemoteUserJoined = null
            agoraManager.onRemoteUserLeft = null
            agoraManager.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { viewContext ->
                SurfaceView(viewContext).also { surface ->
                    // The actual remote user is attached after a match.
                    surface.setBackgroundColor(android.graphics.Color.BLACK)
                }
            },
            update = { surface ->
                remoteUid?.let { uid ->
                    agoraManager.setupRemoteVideo(surface, uid)
                }
            }
        )

        if (remoteUid == null) {
            Text(
                text = callMessage,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.DarkGray.copy(alpha = 0.8f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "PNG Connect",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = callMessage,
                    fontSize = 11.sp,
                    color = PngGoldPrimary
                )
            }

            IconButton(
                onClick = onOpenSafety,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.DarkGray)
            ) {
                Icon(
                    Icons.Default.Report,
                    contentDescription = "Report or safety",
                    tint = AlertRed
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 110.dp)
                .size(110.dp, 150.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF22252D))
                .border(2.dp, PngGoldPrimary, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (permissionGranted && !isCameraOff) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { viewContext ->
                        SurfaceView(viewContext).also { surface ->
                            agoraManager.setupLocalVideo(surface)
                        }
                    },
                    update = { surface ->
                        if (permissionGranted && !isCameraOff) {
                            agoraManager.setupLocalVideo(surface)
                        }
                    }
                )
            } else {
                Text(
                    text = if (isCameraOff) "Camera Off" else "Camera Permission Needed",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    isMuted = !isMuted
                    agoraManager.muteMicrophone(isMuted)
                },
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (isMuted) AlertRed else Color(0xFF2B2F3A))
            ) {
                Icon(
                    imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = if (isMuted) "Unmute microphone" else "Mute microphone",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = {
                    isCameraOff = !isCameraOff
                    agoraManager.enableCamera(!isCameraOff)
                },
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (isCameraOff) AlertRed else Color(0xFF2B2F3A))
            ) {
                Icon(
                    imageVector = if (isCameraOff) Icons.Default.VideocamOff else Icons.Default.Videocam,
                    contentDescription = if (isCameraOff) "Turn camera on" else "Turn camera off",
                    tint = Color.White
                )
            }

            Button(
                onClick = onEndCall,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PngGoldPrimary
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(
                    Icons.Default.SkipNext,
                    contentDescription = "Next person",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    "Next",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = onEndCall,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(AlertRed)
            ) {
                Icon(
                    Icons.Default.CallEnd,
                    contentDescription = "End call",
                    tint = Color.White
                )
            }
        }
    }
}
