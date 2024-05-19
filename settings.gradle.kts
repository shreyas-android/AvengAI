pluginManagement {
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
        maven("https://cboost.jfrog.io/artifactory/chartboost-mediation/") {
            name = "Chartboost Mediation's Production Repo"
        }
        maven("https://cboost.jfrog.io/artifactory/chartboost-ads/") {
            name = "Chartboost's maven repo"
        }
    }
}



rootProject.name = "AvengAdProjects"
include(":app")
include(":feature:convertor")

includeBuild("../AvengAdModule") {
    dependencySubstitution {
        substitute(module("sdk_V1:avenger-ad")).using(project(":avengerad"))
        substitute(module("sdk_V1:ui")).using(project(":ui"))
        substitute(module("sdk_v1:feature-applovin-run-screen")).using(project(":feature:applovinscreen"))
    }
}

