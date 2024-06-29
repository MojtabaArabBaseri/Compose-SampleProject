package io.millennium.gradle

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {

        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeVersion").get().toString()
        }

        dependencies {

            val bom = libs.findLibrary("androidx.compose.bom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findLibrary("androidx.activity.compose").get())
            //Lifecycle
            add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
            //Ui
            add("implementation", libs.findLibrary("androidx.compose.ui.ui.tooling").get())
            //Navigation
            add("implementation", libs.findLibrary("androidx.navigation.compose").get())
            add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
            //Test
            add("androidTestImplementation", platform(bom))
        }
    }
}