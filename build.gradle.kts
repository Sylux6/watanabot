val atriumVersion = "0.9.1"
val azurapiVersion = "3.1.3"
val exposedVersion = "0.21.1"
val harmonicaVersion = "develop-SNAPSHOT"
val imageboardVersion = "2.1"
val javaStringSimilarityVersion = "1.2.1"
val jdaVersion = "4.1.1_108"
val jdaReactorVersion = "1.0.0"
val jsonVersion = "20190722"
val konfigVersion = "1.6.10.0"
val kotlinLoggingVersion = "1.7.8"
val kotlinVersion = "1.3.61"
val lavaplayerVersion = "1.3.34"
val logbackVersion = "1.2.3"
val mockkVersion = "1.9.3"
val postgresqlVersion = "42.2.10"
val quartzVersion = "2.3.2"
val reflectionsVersion = "0.9.12"
val spekVersion = "2.0.9"

buildscript {
    repositories {
        jcenter()
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-script-util:1.3.61")
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
    implementation("net.dv8tion:JDA:$jdaVersion")
    implementation("com.sedmelluq:lavaplayer:$lavaplayerVersion")
    implementation("club.minnced:jda-reactor:$jdaReactorVersion")

    // Database
    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("org.quartz-scheduler:quartz:$quartzVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")

    // Database migration
    implementation("com.github.KenjiOhtsuka:harmonica:$harmonicaVersion")
    implementation("org.reflections:reflections:$reflectionsVersion")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:$kotlinVersion")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    // Utils
    implementation("org.json:json:$jsonVersion")
    implementation("info.debatty:java-string-similarity:$javaStringSimilarityVersion")
    implementation("com.natpryce:konfig:$konfigVersion")

    // API
    implementation("net.kodehawa:imageboard-api:$imageboardVersion")
    implementation("com.github.AzurAPI:AzurApi-Kotlin:$azurapiVersion")

    // Test
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("ch.tutteli.atrium:atrium-fluent-en_GB:$atriumVersion")
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
	archiveFileName.set("watanabot.jar")
        manifest {
            attributes["Main-Class"] = "com.github.sylux6.watanabot.core.Main"
        }
        dependencies {
            exclude(dependency("com.github.KenjiOhtsuka:harmonica"))
            exclude(dependency("org.reflections:reflections"))
            exclude(dependency("org.jetbrains.kotlin:kotlin-script-runtime"))
        }
       	// Exclude dependencies if there are ClassNotFoundError with them
        minimize {
            exclude(dependency("ch.qos.logback:logback-classic"))
            exclude(dependency("org.quartz-scheduler:quartz"))
            exclude(dependency(("org.jetbrains.exposed:exposed-dao")))
            exclude(dependency(("org.jetbrains.exposed:exposed-jdbc")))
            exclude(dependency("org.postgresql:postgresql"))
        }
    }
    create("cleanLogs", Delete::class) {
        group = "log"
        delete = setOf(
            "yousolog-error.log", "yousolog-info.log"
        )
    }
}
