plugins {
    id("java")
    id("org.springframework.boot") version ("3.3.0")
}

apply(plugin = "io.spring.dependency-management")

group = "com.github.jakubzmuda"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}