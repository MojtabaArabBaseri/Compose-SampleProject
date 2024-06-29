plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.database"
}

dependencies {
    implementation(projects.core.model)
}