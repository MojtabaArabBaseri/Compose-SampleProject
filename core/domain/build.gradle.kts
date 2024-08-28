plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.domain"
}

dependencies {
    api(projects.core.common)
    api(projects.core.data)
}