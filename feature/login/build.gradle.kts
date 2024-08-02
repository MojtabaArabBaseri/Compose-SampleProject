plugins {
    id("io.millennium.gradle.android.feature")
}

android {
    namespace = "ir.millennium.composesample.feature.login"
}

dependencies {

    implementation(projects.core.firebase)

}