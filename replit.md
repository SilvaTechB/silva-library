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
- **Java Runtime**: GraalVM JDK 19 (via `java-graalvm22.3` module)
- **Group ID**: `app.silva`
- **Key Dependencies**:
  - `app.morphe:jadb` — private fork with Shell v2 support (from MorpheApp GitHub Packages)
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
- `GITHUB_TOKEN` — GitHub Personal Access Token with `read:packages` permission

These are needed to:
1. Resolve `app.morphe:jadb` from `maven.pkg.github.com/MorpheApp/registry`
2. Resolve/publish packages at `maven.pkg.github.com/SilvaTechB/silva-library`

## Workflow

- **Build Library**: `./gradlew compileKotlinJvm --no-daemon`
  - Compiles the JVM target of the library
  - Run type: `console`

## Dependency Repositories

- `maven.pkg.github.com/SilvaTechB/silva-library` — primary silva registry
- `maven.pkg.github.com/MorpheApp/registry` — secondary, for resolving `app.morphe:jadb` (until re-published as `app.silva:jadb`)
- `jitpack.io` — for libsu

## Publishing

Artifacts are published to GitHub Packages at `maven.pkg.github.com/SilvaTechB/silva-library`. Publishing uses `GITHUB_ACTOR` and `GITHUB_TOKEN` credentials and GPG signing (`useGpgCmd()`).

Release automation is handled by `semantic-release` (configured in `.releaserc`) via npm scripts.

## Rebrand Notes

Fully rebranded from `app.morphe` → `app.silva`:
- All Kotlin package declarations and imports updated
- AIDL file moved to `app/silva/library/...` path
- API compatibility files renamed to `silva-library.api`
- POM metadata, SCM, and developer info updated to SilvaTechB/silva
- Runtime file paths updated (`/data/adb/silva/`, `/data/local/tmp/silva.tmp`, etc.)
