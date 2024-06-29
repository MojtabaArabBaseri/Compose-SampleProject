plugins {
    id("io.millennium.gradle.android.feature")
}

android {
    namespace = "ir.millennium.composesample.feature.articles"
}

dependencies {
    implementation(projects.core.domain)
}