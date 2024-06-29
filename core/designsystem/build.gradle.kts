plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.designsystem"
}

dependencies {
    api(projects.core.model)
}