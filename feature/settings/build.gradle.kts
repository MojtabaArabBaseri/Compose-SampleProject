plugins {
    id("io.millennium.gradle.android.feature")
}

android {
    namespace = "ir.millennium.composeSample.feature.settings"
}

dependencies {
    implementation(projects.core.designsystem)
}