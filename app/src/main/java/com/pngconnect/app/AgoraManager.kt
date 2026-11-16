package com.pngconnect.app

import android.content.Context
import android.util.Log
import android.view.SurfaceView
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas

class AgoraManager(context: Context) {

    companion object {
        private const val TAG = "PNGConnectAgora"
    }

    private val appContext = context.applicationContext
    private var rtcEngine: RtcEngine? = null

    var onRemoteUserJoined: ((Int) -> Unit)? = null
    var onRemoteUserLeft: ((Int) -> Unit)? = null

    fun initialize(): Boolean {
        if (rtcEngine != null) return true

        val appId = BuildConfig.AGORA_APP_ID

        if (appId.isBlank()) {
            Log.e(TAG, "Agora App ID is missing")
            return false
        }

        return try {
            val config = RtcEngineConfig().apply {
                mContext = appContext
                mAppId = appId
                mEventHandler = object : IRtcEngineEventHandler() {

                    override fun onJoinChannelSuccess(
                        channel: String?,
                        uid: Int,
                        elapsed: Int
                    ) {
                        Log.i(TAG, "Joined channel: $channel")
                    }

                    override fun onUserJoined(uid: Int, elapsed: Int) {
                        Log.i(TAG, "Remote user joined: $uid")
                        onRemoteUserJoined?.invoke(uid)
                    }

                    override fun onUserOffline(uid: Int, reason: Int) {
                        Log.i(TAG, "Remote user left: $uid")
                        onRemoteUserLeft?.invoke(uid)
                    }

                    override fun onError(err: Int) {
                        Log.e(TAG, "Agora error: $err")
                    }
                }
            }

            rtcEngine = RtcEngine.create(config).apply {
                enableVideo()
                enableAudio()
                setChannelProfile(
                    Constants.CHANNEL_PROFILE_COMMUNICATION
                )
                setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
            }

            true
        } catch (exception: Exception) {
            Log.e(TAG, "Could not initialize Agora", exception)
            false
        }
    }

    fun setupLocalVideo(view: SurfaceView) {
        rtcEngine?.setupLocalVideo(
            VideoCanvas(
                view,
                Constants.RENDER_MODE_HIDDEN,
                0
            )
        )
    }

    fun setupRemoteVideo(view: SurfaceView, uid: Int) {
        rtcEngine?.setupRemoteVideo(
            VideoCanvas(
                view,
                Constants.RENDER_MODE_HIDDEN,
                uid
            )
        )
    }

    fun joinTestChannel(channelName: String, uid: Int = 0): Int {
        val engine = rtcEngine ?: return -1

        val options = ChannelMediaOptions().apply {
            autoSubscribeAudio = true
            autoSubscribeVideo = true
            publishCameraTrack = true
            publishMicrophoneTrack = true
        }

        engine.startPreview()

        val result = engine.joinChannel(
            null,
            channelName,
            uid,
            options
        )

        Log.i(TAG, "Join channel result: $result")
        return result
    }

    fun muteMicrophone(muted: Boolean) {
        rtcEngine?.muteLocalAudioStream(muted)
    }

    fun enableCamera(enabled: Boolean) {
        rtcEngine?.enableLocalVideo(enabled)

        if (enabled) {
            rtcEngine?.startPreview()
        } else {
            rtcEngine?.stopPreview()
        }
    }

    fun leaveChannel() {
        rtcEngine?.leaveChannel()
        rtcEngine?.stopPreview()
    }

    fun release() {
        leaveChannel()
        rtcEngine?.let {
            RtcEngine.destroy()
        }
        rtcEngine = null
    }
}
