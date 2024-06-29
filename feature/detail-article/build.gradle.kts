plugins {
    id("io.millennium.gradle.android.feature")
}

android {
    namespace = "ir.millennium.composesample.feature.detail_article"
}

dependencies {
    implementation(projects.core.domain)
}