plugins {
    id("io.quarkus")
    skullprogrammer.javaConventions
    skullprogrammer.quarkusConventions
    //id("java")
}

val quarkusProfile: String by project

repositories {
    mavenCentral()
}

dependencies {
//    implementation("io.quarkus:quarkus-mongodb-panache")
//    annotationProcessor("io.quarkus:quarkus-panache-common")
}
