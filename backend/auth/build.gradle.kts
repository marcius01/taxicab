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
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    implementation("commons-codec:commons-codec:1.17.1")

}