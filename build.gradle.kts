import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    libs.plugins.run {
        alias(kt.jvm)
        alias(kt.serialization)
    }
    id("maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(libs.kt.serialization.json)
    implementation(libs.kt.coroutines)
    implementation(libs.kt.jdk8)
    implementation(libs.logging)
    implementation(libs.playwright)
    implementation(libs.excel4j)

    implementation(libs.mockito)
    testImplementation(libs.kt.test)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
kotlin {
    jvmToolchain(11)
}