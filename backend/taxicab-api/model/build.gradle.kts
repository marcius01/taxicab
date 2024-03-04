plugins {
    java
    id("io.quarkus")
    skullprogrammer.javaConventions
    //id("java")
}

repositories {
    mavenCentral()
}

dependencies {
}

tasks.test {
    useJUnitPlatform()
}