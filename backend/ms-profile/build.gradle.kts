plugins {
    id("io.quarkus")
    skullprogrammer.javaConventions
    skullprogrammer.quarkusConventions
}

val quarkusProfile: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(path=":model"))
    implementation(project(path=":coreFramework"))
}