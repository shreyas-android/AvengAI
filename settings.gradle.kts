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

rootProject.name = "AdGalaxy"

include(":app")
/*include(":avengerad")
include(":feature:applovinscreen")*/
include(":feature:convertor")
include(":aicore")
include(":feature:avengai")
include(":ui")
include(":utils")
include(":feature:chat")
include(":core")
include(":core:chat")
