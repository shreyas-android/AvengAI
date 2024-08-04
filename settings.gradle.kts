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
include(":ui")
include(":feature:chat")
include(":core")
include(":core:chat")
include(":feature:imageai")
include(":feature:inspireai")
