import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "io.millennium.gradle"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
//    compileOnly(libs.firebase.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "io.millennium.gradle.android.application"
            implementationClass = "io.millennium.gradle.plugins.topLevel.ApplicationPlugin"
        }

        register("androidFeature") {
            id = "io.millennium.gradle.android.feature"
            implementationClass = "io.millennium.gradle.plugins.topLevel.FeaturePlugin"
        }
        register("androidCore") {
            id = "io.millennium.gradle.android.core"
            implementationClass = "io.millennium.gradle.plugins.topLevel.CorePlugin"
        }

        register("androidApplicationCompose") {
            id = "io.millennium.gradle.android.application.compose"
            implementationClass = "io.millennium.gradle.plugins.ApplicationComposePlugin"
        }

        register("androidLibraryCompose") {
            id = "io.millennium.gradle.android.library.compose"
            implementationClass = "io.millennium.gradle.plugins.LibraryComposePlugin"
        }

        register("androidLibrary") {
            id = "io.millennium.gradle.android.library"
            implementationClass = "io.millennium.gradle.plugins.LibraryPlugin"
        }

        register("androidHilt") {
            id = "io.millennium.gradle.android.hilt"
            implementationClass = "io.millennium.gradle.plugins.HiltPlugin"
        }

        register("jvmLibrary") {
            id = "io.millennium.gradle.jvm.library"
            implementationClass = "io.millennium.gradle.plugins.LibraryJvmPlugin"
        }

        register("roomLibrary") {
            id = "io.millennium.gradle.android.room"
            implementationClass = "io.millennium.gradle.plugins.RoomPlugin"
        }
    }
}