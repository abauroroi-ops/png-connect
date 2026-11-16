plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.pngconnect.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pngconnect.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Keys from environment variables or Gradle properties (never hard-code secrets)
        val agoraAppId = System.getenv("AGORA_APP_ID")
            ?: (project.findProperty("AGORA_APP_ID") as String?)
            ?: ""
        val supabaseUrl = System.getenv("SUPABASE_URL")
            ?: (project.findProperty("SUPABASE_URL") as String?)
            ?: ""

        buildConfigField("String", "AGORA_APP_ID", "\"$agoraAppId\"")
        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.accompanist.permissions)
    implementation(libs.kotlinx.coroutines.android)

    // Agora Video SDK
    implementation("io.agora.rtc:full-sdk:4.7.0")

    debugImplementation(libs.androidx.compose.ui.tooling)
}
