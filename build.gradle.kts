import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val jacksonVersion: String by project

plugins {
    base
    kotlin("jvm") version "1.4.21"
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = "com.shapegames.common.errors.model"
version = "0.0.1-SNAPSHOT"

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            javaParameters = true
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
}
