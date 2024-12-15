import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = "mug.bibus"
version = "0.1.6"

repositories {
    mavenCentral()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://repo.panda-lang.org/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    implementation("dev.rollczi:litecommands-bukkit:3.9.1")
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("");
    archiveFileName.set(project.name + "-" + project.version + ".jar")
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}