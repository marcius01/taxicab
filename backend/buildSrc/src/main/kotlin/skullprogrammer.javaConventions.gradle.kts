plugins {
    java
}

repositories {
    mavenLocal()
    mavenCentral()
}

group = "tech.skullprogrammer.taxicab"
version = "0.1.0-SNAPSHOT"

val bsonVersion: String by project
val lombokVersion: String by project
val slf4jVersion: String by project
val jacksonVersion: String by project
val junitVersion: String by project
val gsonVersion: String by project
val commonsLang3: String by project

dependencies {
    compileOnly("org.mongodb:bson:$bsonVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    implementation("org.apache.commons:commons-lang3:$commonsLang3")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.test {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.compileTestJava {
    options.encoding = "UTF-8"
}

tasks.test {
    testLogging {
        showStandardStreams = true
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
