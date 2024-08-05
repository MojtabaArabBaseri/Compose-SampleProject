plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.data"
}

dependencies {
    api(projects.core.network)
    api(projects.core.database)
}