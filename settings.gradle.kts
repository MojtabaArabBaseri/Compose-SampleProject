pluginManagement {
    includeBuild("gradle/build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven ( "https://jitpack.io" )
        maven ("https://maven.google.com")
    }
}

rootProject.name = "ComposeSample"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:utils")
include(":core:datastore")
include(":core:model")
include(":core:domain")
include(":core:network")
include(":core:data")
include(":core:database")
include(":feature:splash")
include(":feature:login")
include(":core:designsystem")
include(":feature:main")
include(":feature:aboutme")
include(":feature:articles")
include(":feature:detail-article")
