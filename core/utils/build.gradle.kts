plugins {
    id("io.millennium.gradle.android.core")
}

android {
    namespace = "ir.millennium.composesample.core.utils"
}

dependencies {
    api(projects.core.datastore)
    api(projects.core.model)
}