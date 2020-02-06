val kotlinVersion = "1.3.61"
val spekVersion = "2.0.9"

plugins {
    application
    java
    `maven-publish`
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.jmailen.kotlinter") version "2.3.0"
}

application {
    mainClassName = "com.github.sylux6.watanabot.core.Main"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://jitpack.io")
    maven("https://dl.bintray.com/kodehawa/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("net.dv8tion:JDA:4.1.1_105")
    implementation("com.sedmelluq:lavaplayer:1.3.34")
    implementation("club.minnced:jda-reactor:1.0.0")

    implementation("org.postgresql", "postgresql", "42.2.9")
    implementation("org.hibernate:hibernate-core:5.4.8.Final")
    implementation("org.quartz-scheduler:quartz:2.3.2")

    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.json", "json", "20190722")
    implementation("info.debatty", "java-string-similarity", "1.2.1")
    implementation("com.natpryce:konfig:1.6.10.0")

    implementation("net.kodehawa:imageboard-api:2.1")
    implementation("com.github.AzurAPI:AzurApi-Kotlin:3.1.2")

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
