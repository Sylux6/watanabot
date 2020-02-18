val kotlinVersion = "1.3.61"
val spekVersion = "2.0.9"

buildscript {
    repositories {
        jcenter()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("org.jetbrains.kotlin", "kotlin-script-util", "1.3.61")
        classpath("com.github.KenjiOhtsuka:harmonica:develop-SNAPSHOT")
        classpath("com.github.cesarferreira:kotlin-pluralizer:0.2.9")
    }
}

apply(plugin = "jarmonica")

plugins {
    application
    java
    maven
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.jmailen.kotlinter") version "2.3.0"
}

application {
    mainClassName = "com.github.sylux6.watanabot.core.Main"
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
    maven("https://dl.bintray.com/kodehawa/maven")
}

dependencies {
    // Core
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    // Discord
    implementation("net.dv8tion:JDA:4.1.1_108")
    implementation("com.sedmelluq:lavaplayer:1.3.34")
    implementation("club.minnced:jda-reactor:1.0.0")

    // Database
    implementation("org.postgresql", "postgresql", "42.2.10")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("org.jetbrains.exposed", "exposed-core", "0.21.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.21.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.21.1")
    implementation("org.jetbrains.exposed", "exposed-jodatime", "0.21.1")
    implementation("com.github.KenjiOhtsuka:harmonica:develop-SNAPSHOT")
    implementation("org.reflections", "reflections", "0.9.12")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.3.61")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.7.8")

    // Utils
    implementation("org.json", "json", "20190722")
    implementation("info.debatty", "java-string-similarity", "1.2.1")
    implementation("com.natpryce:konfig:1.6.10.0")

    // API
    implementation("net.kodehawa:imageboard-api:2.1")
    implementation("com.github.AzurAPI:AzurApi-Kotlin:3.1.2")

    // Test
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:0.9.1")
}

tasks {
    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        manifest {
            attributes["Main-Class"] = "com.github.sylux6.watanabot.core.Main"
        }
    }
    create("cleanLogs", Delete::class) {
        group = "log"
        delete = setOf(
            "yousolog-error.log", "yousolog-info.log"
        )
    }
}
