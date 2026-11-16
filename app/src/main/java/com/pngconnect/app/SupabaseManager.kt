package com.pngconnect.app

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class SupabaseManager {

    companion object {
        private const val TAG = "PNGConnectSupabase"
    }

    private val supabaseUrl = BuildConfig.SUPABASE_URL.trimEnd('/')

    suspend fun testConnection(): Boolean = withContext(Dispatchers.IO) {
        if (supabaseUrl.isBlank()) {
            Log.e(TAG, "Supabase URL is missing")
            return@withContext false
        }

        var connection: HttpURLConnection? = null

        try {
            connection = URL("$supabaseUrl/auth/v1/health")
                .openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000

            val status = connection.responseCode
            Log.i(TAG, "Supabase health check returned: $status")

            status in 200..299
        } catch (exception: Exception) {
            Log.e(TAG, "Supabase connection test failed", exception)
            false
        } finally {
            connection?.disconnect()
        }
    }
}
