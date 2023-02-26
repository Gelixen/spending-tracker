plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "lt.kslipaitis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.9.2")
    testImplementation("org.junit.jupiter", "junit-jupiter-params", "5.9.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}