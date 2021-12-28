import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("groovy") 
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("io.micronaut.application") version "3.0.2"
    id("com.google.protobuf") version "0.8.15"
    kotlin("jvm") version "1.6.10"
}

version = "0.1"
group = "br.com.yuri"

repositories {
    mavenCentral()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("br.com.yuri.*")
    }
}

dependencies {
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.groovy:micronaut-runtime-groovy")
    implementation("io.micronaut.grpc:micronaut-grpc-runtime")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("jakarta.annotation:jakarta.annotation-api")
    compileOnly("io.micronaut.data:micronaut-data-processor")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.h2database:h2")
    implementation("io.micronaut:micronaut-validation")

    testImplementation("io.micronaut:micronaut-http-client")
    implementation(kotlin("stdlib-jdk8"))

}


application {
    mainClass.set("br.com.yuri.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
    targetCompatibility = JavaVersion.toVersion("11")
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.17.2"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.39.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc")
            }
        }
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}