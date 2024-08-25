plugins {
    id("io.millennium.gradle.android.feature")
}

android {
    namespace = "ir.millennium.composesample.feature.main"
}

dependencies {
    implementation(projects.core.firebase)
}