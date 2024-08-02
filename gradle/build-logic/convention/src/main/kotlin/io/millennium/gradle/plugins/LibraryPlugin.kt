package io.millennium.gradle.plugins

import com.android.build.gradle.LibraryExtension
import io.millennium.gradle.Versions.TARGET_SDK
import io.millennium.gradle.configureKotlinAndroid
import io.millennium.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid()
                defaultConfig.targetSdk = TARGET_SDK
            }

            dependencies {

                add("implementation", libs.findLibrary("timber").get())
                add("implementation", libs.findLibrary("viewpump").get())
                add("implementation", libs.findLibrary("androidx.multidex").get())
                add("implementation", libs.findLibrary("converter.gson").get())
                //Ui
                add("implementation", libs.findLibrary("androidx.constraintlayout.compose").get())
                add("implementation", libs.findLibrary("androidx.material3").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("accompanist.systemuicontroller").get())
                add("implementation", libs.findLibrary("androidx.ui.graphics").get())
                add("implementation", libs.findLibrary("androidx.ui.tooling").get())
                // Paging
                add("implementation", libs.findLibrary("androidx.paging.runtime.ktx").get())
                add("implementation", libs.findLibrary("androidx.paging.compose").get())
                //Firebase
                add("implementation", platform(libs.findLibrary("firebase.bom").get()))
                add("implementation", libs.findLibrary("firebase.analytics").get())
                add("implementation", libs.findLibrary("firebase.crashlytics").get())
                add("implementation", libs.findLibrary("firebase.crashlytics.ktx").get())
                add("implementation", libs.findLibrary("firebase.messaging.ktx").get())
                add("implementation", libs.findLibrary("firebase.auth").get())
                add("implementation", libs.findLibrary("play.services.auth").get())
                //Test
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
            }
        }
    }
}