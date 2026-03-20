plugins {
    java
    id("org.springframework.boot") version "4.0.4"
    id("io.spring.dependency-management") version "1.1.7"
    id("avro.codegen.plugin")
}

group = "com.goracy"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-kafka")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("io.confluent:kafka-avro-serializer:8.1.1")
    implementation("io.confluent:kafka-schema-registry-client:8.1.1")
    implementation("org.apache.avro:avro:1.12.1")
    implementation("com.clickhouse:clickhouse-jdbc:0.9.8")
    runtimeOnly("org.flywaydb:flyway-database-clickhouse:10.24.0")
    compileOnly("org.projectlombok:lombok:1.18.44")
    annotationProcessor("org.projectlombok:lombok:1.18.44")

    testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
    testCompileOnly("org.projectlombok:lombok:1.18.44")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.44")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

configurations.configureEach {
    resolutionStrategy.capabilitiesResolution.withCapability("org.lz4:lz4-java") {
        selectHighestVersion()
    }
}

tasks.test {
    useJUnitPlatform()
}