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
    }
}

rootProject.name = "AvengOfficialProjects"

include(":app")
include(":aicore")
include(":feature:nlpai")
include(":utils")
include(":feature:chat")
include(":core")
include(":core:chat")
include(":feature:imageai")

includeBuild("../AvengAdModule") {
    dependencySubstitution {
        substitute(module("sdk_V1:avenger-ad")).using(project(":avengerad"))
        substitute(module("sdk_V1:ui")).using(project(":ui"))
    }
}
include(":feature:inspireai")
