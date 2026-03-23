package com.goracy.config;

import com.goracy.Application;
import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.clickhouse.ClickHouseContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@SpringBootTest(classes = Application.class)
public abstract class TestcontainerBaseTest {
    private final static String DOCKER_KAFKA = "apache/kafka-native:4.0.2";
    private final static String DOCKER_SCHEMA_REGISTRY = "confluentinc/cp-schema-registry:7.9.6";
    private final static String DOCKER_CLICK_HOUSE = "clickhouse/clickhouse-server:25.8-alpine";
    private final static String DOCKER_REDIS = "redis:6.2.6";

    private static final Network network = Network.newNetwork();

    @Container
    @ServiceConnection
    private static final KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse(DOCKER_KAFKA))
            .withNetwork(network)
            .withNetworkAliases("kafka")
            .withReuse(true);

    @Container
    @SuppressWarnings("resource")
    private static final GenericContainer<?> schemaRegistry = new GenericContainer<>(
            DockerImageName.parse(DOCKER_SCHEMA_REGISTRY))
            .withNetworkAliases("schema-registry")
            .withNetwork(network)
            .withExposedPorts(8081)
            .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://kafka:9093")
            .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
            .dependsOn(kafka)
            .withReuse(true);

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final ClickHouseContainer clickHouse = new ClickHouseContainer(
            DockerImageName.parse(DOCKER_CLICK_HOUSE))
            .withNetworkAliases("clickHouse")
            .withReuse(true);

    @Container
    @ServiceConnection
    @SuppressWarnings("unused")
    private static final RedisContainer redis = new RedisContainer(
            DockerImageName.parse(DOCKER_REDIS))
            .withNetworkAliases("redis")
            .withReuse(true);

    @DynamicPropertySource
    private static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.properties.schema.registry.url", () ->
                String.format("http://%s:%d", schemaRegistry.getHost(), schemaRegistry.getFirstMappedPort()));
    }
}