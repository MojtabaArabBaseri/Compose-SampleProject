plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.common"
}

dependencies {
    implementation(projects.core.model)
}