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
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-kafka")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // Libraries
    compileOnly("org.projectlombok:lombok:1.18.44")
    annotationProcessor("org.projectlombok:lombok:1.18.44")

    // Kafka
    implementation("io.confluent:kafka-avro-serializer:8.1.1")
    implementation("io.confluent:kafka-schema-registry-client:8.1.1")
    implementation("org.apache.avro:avro:1.12.1")

    // Clickhouse
    implementation("com.clickhouse:clickhouse-jdbc:0.9.8")
    runtimeOnly("org.flywaydb:flyway-database-clickhouse:10.24.0")

    // Testcontainers
    testImplementation("org.testcontainers:junit-jupiter:1.21.4")
    testImplementation("org.testcontainers:kafka:1.21.4")
    testImplementation("org.testcontainers:clickhouse:1.21.4")
    testImplementation("com.redis:testcontainers-redis:2.2.4")

    // Test libraries
    testCompileOnly("org.projectlombok:lombok:1.18.44")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.44")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Spring tests
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

configurations.configureEach {
    resolutionStrategy.capabilitiesResolution.withCapability("org.lz4:lz4-java") {
        selectHighestVersion()
    }
}

tasks.test {
    useJUnitPlatform()
}