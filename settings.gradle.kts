pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("kotlin", "1.9.23")
            version("kotlin-serialization", "1.6.3")
            version("kotlinx-coroutines", "1.8.0")
            version("logging", "3.0.5")
            version("playwright", "1.41.0")
            version("mockito", "5.12.0")
            version("excel4j", "3.1.0")

            plugin("kt-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kt-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")

            library("kt-test", "org.jetbrains.kotlin", "kotlin-test").versionRef("kotlin")
            library("kt-jdk8", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin")
            library("kt-serialization-json", "org.jetbrains.kotlinx", "kotlinx-serialization-json-jvm").versionRef("kotlin-serialization")
            library("kt-coroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("kotlinx-coroutines")

            library("logging", "io.github.microutils", "kotlin-logging").versionRef("logging")

            library("playwright", "com.microsoft.playwright", "playwright").versionRef("playwright")

            library("excel4j", "com.github.crab2died", "Excel4J").versionRef("excel4j")

            library("mockito", "org.mockito", "mockito-junit-jupiter").versionRef("mockito")
        }
    }
}

rootProject.name = "societedotcom-wrapper"

