plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.network"
}

dependencies {

    api(projects.core.utils)
    api(projects.core.model)
    api(projects.core.database)
}