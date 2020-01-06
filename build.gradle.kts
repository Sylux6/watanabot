val kotlinVersion = "1.3.61"

plugins {
    java
    maven
    kotlin("jvm") version "1.3.61"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    jcenter()
    maven(url="https://jitpack.io")
    maven (url="https://dl.bintray.com/kodehawa/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.postgresql", "postgresql", "42.2.2")
    implementation("net.dv8tion:JDA:4.1.0_95")
    implementation("com.sedmelluq:lavaplayer:1.3.33")
    implementation("net.kodehawa:imageboard-api:2.1")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("org.hibernate:hibernate-core:5.4.8.Final")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.json", "json", "20190722")
    implementation("com.github.AzurAPI:AzurApi-Kotlin:3.0.0")

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("io.mockk:mockk:1.9.3")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}