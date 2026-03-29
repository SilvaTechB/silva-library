<div align="center">

# Silva Library

[![GitHub release](https://img.shields.io/github/v/release/SilvaTechB/silva-library?style=for-the-badge)](https://github.com/SilvaTechB/silva-library/releases)
[![GitHub Packages](https://img.shields.io/badge/GitHub_Packages-gray?style=for-the-badge&logo=github)](https://github.com/SilvaTechB/silva-library/packages)
[![License](https://img.shields.io/badge/License-GPL_v3-blue?style=for-the-badge)](LICENSE)

</div>

# 📚 Silva Library
Library containing common utilities for Silva.

&nbsp;
## ❓ About

Silva Library powers projects such as [Silva Manager](https://github.com/SilvaTechB/silva-manager) with common utilities and functionalities by providing shared code.

Silva Library is based off the prior work of [ReVanced](https://github.com/ReVanced/revanced-library) and [Morphe](https://github.com/MorpheApp/morphe-library). All modifications, along with their dates, can be found in the Git history.

## 💪 Features

Some of the features Silva Library provides are:

- 📝 **Signing APKs**: Read and write keystores, and sign APK files
- 🧩 **Common utility functions**: Various APIs for Silva patches such as JSON serialization,
  reading and setting patch options, calculating the most common compatible version for a set of patches and more
- 💾 **Install and uninstall APKs**: Install and uninstall APK files via ADB or locally,
  the Android package manager, or by mounting using root permissions
- 📦 **Repackage patched files to an APK**: Apply patched files from
  [Silva Patcher](https://github.com/SilvaTechB/silva-patcher) to an APK file, and align & sign the APK file automatically

## 🚀 How to get started

To use Silva Library in your project, follow these steps:

1. Add the GitHub Packages repository to your `build.gradle.kts`:

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
   ```

2. Add the dependency:

   ```kotlin
   dependencies {
       // For Android projects:
       implementation("app.silva:silva-library-android:1.3.0")
       // For JVM projects:
       implementation("app.silva:silva-library-jvm:1.3.0")
   }
   ```

## 📚 Everything else

### 📙 Contributing

Thank you for considering contributing to Silva Library.
You can find the contribution guidelines [here](CONTRIBUTING.md).

### 🛠️ Building

To build Silva Library, clone the repository and run:

```bash
./gradlew compileKotlinJvm
```

To publish artifacts to GitHub Packages:

```bash
export ANDROID_HOME=/path/to/android-sdk
./gradlew publish
```

You will need `GITHUB_ACTOR` and `GITHUB_TOKEN` environment variables set with `write:packages` permission.

## 📜 License

Silva Library is licensed under the [GNU General Public License v3.0](LICENSE), with additional conditions under GPLv3 Section 7.

See the [LICENSE](LICENSE) file for the full GPLv3 terms and the [NOTICE](NOTICE) file for full conditions of GPLv3 Section 7.
