package io.millennium.gradle.plugins.topLevel

import com.android.build.api.dsl.ApplicationExtension
import io.millennium.gradle.Versions
import io.millennium.gradle.configureKotlinAndroid
import io.millennium.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.gms.google-services")
                apply("com.google.firebase.crashlytics")

                apply("io.millennium.gradle.android.application.compose")
                apply("io.millennium.gradle.android.hilt")

            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid()

                defaultConfig {
                    targetSdk = Versions.TARGET_SDK
                }
            }

            dependencies {

                add("implementation", libs.findLibrary("androidx.material3").get())
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("accompanist.systemuicontroller").get())
                add("implementation", libs.findLibrary("androidx.multidex").get())
                add("implementation", libs.findLibrary("timber").get())
                //Firebase
                add("implementation", platform(libs.findLibrary("firebase.bom").get()))
                add("implementation", libs.findLibrary("firebase.analytics").get())
                add("implementation", libs.findLibrary("firebase.crashlytics").get())
                add("implementation", libs.findLibrary("firebase.crashlytics.ktx").get())
                add("implementation", libs.findLibrary("firebase.messaging.ktx").get())
                //Test
                add("testImplementation", libs.findLibrary("junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
            }
        }
    }
}