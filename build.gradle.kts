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
    maven("https://jitpack.io")
    maven ("https://dl.bintray.com/kodehawa/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    implementation("net.dv8tion:JDA:4.1.1_101")
    implementation("com.sedmelluq:lavaplayer:1.3.33")

    implementation("org.hibernate:hibernate-core:5.4.8.Final")
    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("org.postgresql", "postgresql", "42.2.9")
    implementation("org.json", "json", "20190722")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("net.kodehawa:imageboard-api:2.1")
    implementation("com.github.AzurAPI:AzurApi-Kotlin:3.1.2")

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

tasks.create<Delete>("cleanLogs") {
    group = "log"
    delete = setOf (
            "yousolog-error.log", "yousolog-info.log"
    )
}