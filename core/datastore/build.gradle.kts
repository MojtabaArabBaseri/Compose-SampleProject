plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.datastore"
}

dependencies {
    api(projects.core.model)
}