import org.gradle.jvm.toolchain.JvmVendorSpec

plugins {
    id("java")
    id("java-library")
    eclipse
}

group = "de.florianmichael"
version = "1.0.0"
description = "ViaErrorHandler"

val apiVersion = "1.19"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        vendor.set(JvmVendorSpec.GRAAL_VM)
    }
    withSourcesJar()
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.viaversion.com") }
    maven { url = uri("https://repo.purpurmc.org/snapshots") }
}

dependencies {
    compileOnly("com.viaversion:viaversion-api:4.6.2")
    compileOnly("org.purpurmc.purpur:purpur-api:1.19.4-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.processResources {
    filteringCharset = "UTF-8"
    val props = mapOf(
        "version" to project.version.toString(),
        "apiVersion" to apiVersion
    )
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.jar {
    archiveFileName.set("ViaErrorHandler-1.0.jar")
}

