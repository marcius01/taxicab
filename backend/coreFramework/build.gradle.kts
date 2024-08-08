plugins {
    id("java")
    id("io.quarkus")
}

group = "tech.skullprogrammer"
version = "0.1"

repositories {
    mavenCentral()
}
val lombokVersion: String by project
val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
//    implementation("io.quarkus:quarkus-hibernate-validator")
//    implementation("io.quarkus:quarkus-rest-client-reactive-jackson")

    testImplementation("io.quarkus:quarkus-junit5")
}

tasks.test {
    useJUnitPlatform()
}