# Morphe Library

A Kotlin Multiplatform (KMP) library providing common utilities for the Morphe ecosystem (Morphe Manager, Morphe CLI). It is a fork of the ReVanced Library.

## Purpose

Handles Android-specific tasks and general utilities including:
- **APK Management**: Install/uninstall APKs via ADB, local package manager, or root
- **Shell Execution**: Run commands via ADB shell or local root shell
- **Patching Utilities**: JSON serialization, patch options, version compatibility
- **APK Signing**: Read/write keystores, sign and align APKs

## Tech Stack

- **Language**: Kotlin (Multiplatform — targets `jvm` and `android`)
- **Build System**: Gradle 8.9 (Kotlin DSL)
- **Java Runtime**: GraalVM JDK 19 (via `java-graalvm22.3` module)
- **Key Dependencies**:
  - `app.morphe:jadb` — private fork with Shell v2 support (from GitHub Packages)
  - `com.github.topjohnwu.libsu` — root shell access on Android
  - `com.google.guava` — core Java/Kotlin utilities
  - `org.jetbrains.kotlin:kotlin-reflect`
  - `androidx.core:core-ktx`

## Project Layout

```
src/
  androidMain/   — Android-specific implementations + AIDL files
  commonMain/    — Shared interfaces and logic (JVM + Android)
gradle/
  libs.versions.toml  — Centralized dependency versions
api/
  morphe-library.api  — Binary compatibility API surface file
```

## Required Secrets

- `GITHUB_ACTOR` — GitHub username for authenticating with GitHub Packages
- `GITHUB_TOKEN` — GitHub Personal Access Token with `read:packages` permission

These are needed to resolve the private `app.morphe:jadb` dependency from `maven.pkg.github.com/MorpheApp/registry`.

## Workflow

- **Build Library**: `./gradlew compileKotlinJvm --no-daemon`
  - Compiles the JVM target of the library
  - Run type: `console`

## Publishing

Artifacts are published to GitHub Packages at `maven.pkg.github.com/MorpheApp/morphe-library`. Publishing uses `GITHUB_ACTOR` and `GITHUB_TOKEN` credentials and GPG signing (`useGpgCmd()`).

Release automation is handled by `semantic-release` (configured in `.releaserc`) via npm scripts.
