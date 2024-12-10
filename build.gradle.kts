plugins {
    id("java")
}

group = "mug.bibus"
version = "0.0.1"

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