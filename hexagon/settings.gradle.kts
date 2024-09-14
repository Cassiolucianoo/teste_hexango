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
        google() // Defina aqui os repositórios que serão utilizados
        mavenCentral()
    }
}

rootProject.name = "hexagon"
include(":app")
