pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name="taxicab"
include("coreFramework")
include("taxicab-api:api")
include("model")
//findProject(":taxicab-api:api")?.name = "api"
include("auth")
//findProject(":taxicab-api:auth")?.name = "auth"
include("ms-profile")
