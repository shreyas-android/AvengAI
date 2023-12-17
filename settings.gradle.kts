pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven{
            url = uri("https://android-sdk.is.com/")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
        maven{
            url = uri("https://android-sdk.is.com/")

        }

        maven{
            url = uri("https://cboost.jfrog.io/artifactory/chartboost-mediation")
        }
    }
}

rootProject.name = "AdGalaxy"

include(":app")
include(":avengerad")
include(":feature:applovinscreen")
include(":feature:convertor")
