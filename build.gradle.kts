import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
    signing
}

group = "app.silva"

// Because access to the project is necessary to authenticate with GitHub,
// the following block must be placed in the root build.gradle.kts file
// instead of the settings.gradle.kts file inside the dependencyResolutionManagement block.
repositories {
    mavenLocal()
    mavenCentral()
    google()
    maven {
        url = uri("https://maven.pkg.github.com/SilvaTechB/silva-library")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
    maven {
        // Required to resolve app.morphe:jadb until it is re-published under app.silva.
        url = uri("https://maven.pkg.github.com/MorpheApp/registry")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
        }
    }
    maven { url = uri("https://jitpack.io") }
}

kotlin {
    jvm {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }

    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }

        publishLibraryVariants("release")
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.core.ktx)
            implementation(libs.libsu.nio)
            implementation(libs.libsu.service)
        }

        commonMain.dependencies {
            implementation(libs.guava)
            implementation(libs.jadb)
            implementation(libs.kotlin.reflect)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test.junit)
        }
    }
}

android {
    namespace = "app.silva.library"
    compileSdk = 35
    defaultConfig {
        minSdk = 26
    }

    buildFeatures {
        aidl = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Use JDK 17 (auto-provisioned if not found) for Android compilation.
// GraalVM's jlink is incompatible with AGP's JdkImageTransform; a standard JDK is required.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/SilvaTechB/silva-library")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    // The KMP plugin automatically creates publications for each target:
    //   - "jvm"            → artifact ID: silva-library-jvm
    //   - "androidRelease" → artifact ID: silva-library-android
    //   - "kotlinMultiplatform" → artifact ID: silva-library (metadata)
    // We configure the shared POM here and pin the artifact IDs explicitly.
    publications.withType<MavenPublication>().configureEach {
        artifactId = when (name) {
            "jvm"                  -> "silva-library-jvm"
            "androidRelease"       -> "silva-library-android"
            "kotlinMultiplatform"  -> "silva-library"
            else                   -> artifactId
        }

        pom {
            name = "Silva Library"
            description = "Library containing common utilities for Silva"
            url = "https://github.com/SilvaTechB/silva-library"

            licenses {
                license {
                    name = "GNU General Public License v3.0"
                    url = "https://www.gnu.org/licenses/gpl-3.0.en.html"
                    comments = "Additional conditions under GPL section 7 apply: attribution and project name restrictions. See LICENSE file."
                }
            }

            developers {
                developer {
                    id = "Silva"
                    name = "Silva"
                    email = "contact@silva.app"
                }
            }

            scm {
                connection = "scm:git:git://github.com/SilvaTechB/silva-library.git"
                developerConnection = "scm:git:git@github.com:SilvaTechB/silva-library.git"
                url = "https://github.com/SilvaTechB/silva-library"
            }
        }
    }
}

// Sign only when a GPG key is available (in-memory, via env vars).
// Set GPG_SIGNING_KEY (armored private key) and GPG_SIGNING_PASSWORD in your secrets.
val signingKey = System.getenv("GPG_SIGNING_KEY")
val signingPassword = System.getenv("GPG_SIGNING_PASSWORD")
if (!signingKey.isNullOrEmpty()) {
    signing {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}
