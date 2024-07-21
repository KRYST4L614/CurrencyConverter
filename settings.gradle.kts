pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "CurrencyConverter"
include(":app")
include(":feature:result")
include(":feature:choosecurrency")
include(":shared:animations")
include(":shared:fragmentdependencies")
include(":shared:currency")
include(":shared:resourceprovider")
include(":component:resources")
 