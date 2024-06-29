package io.millennium.gradle.plugins

import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import io.millennium.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("androidx.room")
            pluginManager.apply("com.google.devtools.ksp")

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.room.runtime").get())
                add("implementation", libs.findLibrary("androidx.room.ktx").get())
                add("implementation", libs.findLibrary("androidx.room.paging").get())
                add("ksp", libs.findLibrary("androidx.room.compiler").get())
            }
        }
    }
}