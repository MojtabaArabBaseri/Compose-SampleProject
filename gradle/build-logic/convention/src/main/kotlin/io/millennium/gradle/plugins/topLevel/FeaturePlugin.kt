package io.millennium.gradle.plugins.topLevel

import io.millennium.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("io.millennium.gradle.android.library")
                apply("io.millennium.gradle.android.hilt")
                apply("io.millennium.gradle.android.library.compose")
                apply("kotlin-parcelize")
            }

            dependencies {

                add("implementation", project(":core:model"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:datastore"))
                //LoadImage
                add("implementation", libs.findLibrary("coil-compose").get())
                //Ui
                add("implementation", libs.findLibrary("accompanist.swiperefresh").get())
                //Lifecycle
                add("implementation", libs.findLibrary("androidx.lifecycle.runtime.ktx").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.ktx").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.livedata.ktx").get())
                //Preview
                add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
                add("implementation", libs.findLibrary("androidx.navigation.runtime.ktx").get())
            }
        }
    }
}