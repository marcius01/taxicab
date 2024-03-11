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
}