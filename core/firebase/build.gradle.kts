plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.firebase"
}

dependencies {
    api(projects.core.model)
}