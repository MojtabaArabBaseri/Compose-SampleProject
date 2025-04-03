dependencyResolutionManagement {
    repositories {
        maven ("https://maven.myket.ir")
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
        maven("https://jitpack.io")
        maven("https://maven.google.com")
    }

    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
}


rootProject.name = "build-logic"
include(":convention")