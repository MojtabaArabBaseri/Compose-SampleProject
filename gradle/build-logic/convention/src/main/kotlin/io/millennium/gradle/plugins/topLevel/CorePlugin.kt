package io.millennium.gradle.plugins.topLevel

import io.millennium.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class CorePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("io.millennium.gradle.android.library")
                apply("io.millennium.gradle.android.library.compose")
                apply("io.millennium.gradle.android.hilt")
                apply("io.millennium.gradle.android.room")
                apply("kotlin-parcelize")
            }

            dependencies {
                //Retrofit
                add("implementation", libs.findLibrary("retrofit").get())
                add("implementation", libs.findLibrary("converter.gson").get())
                add("implementation", platform(libs.findLibrary("okhttp.bom").get()))
                add("implementation", libs.findLibrary("okhttp").get())
                add("implementation", libs.findLibrary("logging.interceptor").get())
                add("implementation", libs.findLibrary("androidx.datastore.preferences").get())
                //Tools
                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("viewpump").get())
                add("implementation", libs.findLibrary("androidx.multidex").get())
            }
        }
    }
}