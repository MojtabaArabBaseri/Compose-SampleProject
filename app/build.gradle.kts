import java.io.FileInputStream
import java.util.Properties

plugins {
    id("io.millennium.gradle.android.application")
}

android {
    namespace = "ir.millennium.composesample"

    defaultConfig {
        applicationId = "ir.millennium.composesample"
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true
    }

    signingConfigs {
        register("release") {
            val properties = Properties()
            properties.load(FileInputStream(project.rootProject.file("local.properties")))
            if (properties.isNotEmpty()) {
                storeFile = file(properties.getProperty("signing.path"))
                storePassword = properties.getProperty("signing.storePassword")
                keyAlias = properties.getProperty("signing.keyAlias")
                keyPassword = properties.getProperty("signing.keyPassword")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes.add("META-INF/*")
        }
    }
}

dependencies {

    implementation(projects.core.utils)
    implementation(projects.core.datastore)
    implementation(projects.core.model)
    implementation(projects.core.designsystem)
    implementation(projects.core.firebase)
    implementation(projects.feature.splash)
    implementation(projects.feature.login)
    implementation(projects.feature.main)
    implementation(projects.feature.aboutme)
    implementation(projects.feature.articles)
    implementation(projects.feature.detailArticle)
    implementation(projects.feature.settings)

}