# Silva Library

A Kotlin Multiplatform (KMP) library providing common utilities for the Silva ecosystem. Forked and rebranded from MorpheApp/morphe-library.

## Purpose

Handles Android-specific tasks and general utilities including:
- **APK Management**: Install/uninstall APKs via ADB, local package manager, or root
- **Shell Execution**: Run commands via ADB shell or local root shell
- **Patching Utilities**: JSON serialization, patch options, version compatibility
- **APK Signing**: Read/write keystores, sign and align APKs

## Tech Stack

- **Language**: Kotlin (Multiplatform — targets `jvm` and `android`)
- **Build System**: Gradle 8.9 (Kotlin DSL)
- **Java Runtime**: GraalVM JDK 19 (daemon), JDK 17 Temurin (Android compilation, auto-provisioned)
- **Android SDK**: Installed at `/home/runner/android-sdk` (platform 35, build-tools 35.0.1)
- **Group ID**: `app.silva`
- **Key Dependencies**:
  - `app.morphe:jadb` — private fork from MorpheApp GitHub Packages
  - `com.github.topjohnwu.libsu` — root shell access on Android
  - `com.google.guava` — core Java/Kotlin utilities
  - `org.jetbrains.kotlin:kotlin-reflect`
  - `androidx.core:core-ktx`

## Project Layout

```
src/
  androidMain/   — Android-specific implementations + AIDL files
    aidl/app/silva/library/...
    kotlin/app/silva/library/...
  commonMain/    — Shared interfaces and logic (JVM + Android)
    kotlin/app/silva/library/...
gradle/
  libs.versions.toml  — Centralized dependency versions
api/
  android/silva-library.api  — Android binary compatibility API surface
  jvm/silva-library.api      — JVM binary compatibility API surface
```

## Required Secrets

- `GITHUB_ACTOR` — GitHub username for authenticating with GitHub Packages
- `GITHUB_TOKEN` — GitHub Personal Access Token with `read:packages` + `write:packages` permission

## Workflow

- **Build Library**: `./gradlew compileKotlinJvm --no-daemon`  
  Compiles the JVM target to verify the code builds correctly.

## Publishing to GitHub Packages

Published artifacts (both already live at v1.3.0):
- `app.silva:silva-library-jvm:1.3.0` — JVM artifact
- `app.silva:silva-library-android:1.3.0` — Android AAR artifact

To publish a new version, bump `version` in `gradle.properties`, then run from the Shell:

```bash
export ANDROID_HOME=/home/runner/android-sdk
./gradlew publishJvmPublicationToGitHubPackagesRepository --no-daemon
./gradlew publishAndroidReleasePublicationToGitHubPackagesRepository --no-daemon
```

Or publish all at once:
```bash
export ANDROID_HOME=/home/runner/android-sdk
./gradlew publish --no-daemon
```

### Notes
- **Signing**: Optional. Set `GPG_SIGNING_KEY` (armored private key) and `GPG_SIGNING_PASSWORD` secrets to enable in-memory GPG signing.
- **Android SDK**: Installed at `/home/runner/android-sdk` (NOT committed to git, must be re-installed on fresh environments).
- **JDK 17 Toolchain**: Auto-downloaded by Gradle on first run for Android compilation (GraalVM's jlink is incompatible with AGP).
- **`local.properties`**: Points to Android SDK path; gitignored and machine-local.

## Dependency Repositories

- `maven.pkg.github.com/SilvaTechB/silva-library` — primary silva registry (publish + resolve)
- `maven.pkg.github.com/MorpheApp/registry` — secondary, for resolving `app.morphe:jadb`
- `jitpack.io` — for libsu

## Using This Library in Other Projects

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/SilvaTechB/silva-library")
        credentials {
            username = System.getenv("GITHUB_ACTOR")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}

dependencies {
    implementation("app.silva:silva-library-android:1.3.0") // Android projects
    // OR
    implementation("app.silva:silva-library-jvm:1.3.0")    // JVM projects
}
```

## Rebrand Notes

Fully rebranded from `app.morphe` → `app.silva`:
- All Kotlin package declarations and imports updated
- AIDL file moved to `app/silva/library/...` path
- API compatibility files renamed to `silva-library.api`
- POM metadata, SCM, and developer info updated to SilvaTechB/silva
- Runtime file paths updated (`/data/adb/silva/`, `/data/local/tmp/silva.tmp`, etc.)
