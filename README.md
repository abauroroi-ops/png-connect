# PNG Connect - Native Android App (Jetpack Compose)

**PNG Connect** is an Android video chat and social platform designed exclusively for adults (18+) across the 22 provinces of Papua New Guinea.

## Color Theme
Inspired by the Papua New Guinea national colors:
- **PNG Red**: `#D21034`
- **PNG Kumul Gold**: `#FCD116`
- **PNG Obsidian Black**: `#0F1015`

## Current Status (Step 1 complete)

The project now builds cleanly. UI screens and navigation are in place. Agora and Supabase skeletons exist but **real matching, authentication, and live video calls are not yet implemented**.

## Screens
1. **WelcomeScreen** – Logo, branding, 18+ notice, Get Started
2. **ProfileSetupScreen** – Nickname, birth year (18+ check), province field
3. **HomeScreen** – Start Video Chat, connection status, settings links
4. **MatchingScreen** – Animated search (currently simulated)
5. **VideoCallScreen** – Video surfaces, Mute / Camera / Next / End / Report UI
6. **CallEndedScreen** – Summary and return options
7. **SafetyScreen** – Community guidelines and report UI
8. **SettingsScreen** – Data saver and privacy toggles

## How to open and build in Android Studio

1. Unzip this project.
2. Open **Android Studio** (Hedgehog or newer).
3. Choose **Open** and select the unzipped folder.
4. Wait for Gradle Sync to finish.
5. Create a file named `local.properties` in the project root (copy from `local.properties.example`) and set your Android SDK path:
   ```
   sdk.dir=/path/to/your/Android/Sdk
   ```
6. (Optional) Add your keys to `local.properties` or as environment variables:
   ```
   AGORA_APP_ID=your_id
   SUPABASE_URL=https://xxxx.supabase.co
   ```
7. Click **Run** on an emulator or physical device (min Android 7.0 / API 24).

### Command line build
```bash
./gradlew assembleDebug
```
The APK will appear at:
`app/build/outputs/apk/debug/app-debug.apk`

## What still needs to be built (next steps)
- Real user registration / login (Supabase Auth)
- Saving profiles to a database
- Real random matching system
- Completing Agora (initialize + join channel with secure token)
- Working Block / Report
- Low-data video quality settings
- Friends & messaging (later)

## Requirements
- Android Studio Hedgehog or newer
- JDK 17
- Min SDK 24, Target SDK 35
