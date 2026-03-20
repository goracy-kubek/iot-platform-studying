plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation("org.apache.avro:avro-tools:1.12.1") {
        exclude(group = "org.apache.avro", module = "trevni-core")
        exclude(group = "org.apache.avro", module = "trevni-avro")
    }
}

gradlePlugin {
    plugins {
        create("avroCodegen") {
            id = "avro.codegen.plugin"
            implementationClass = "AvroCodegenPlugin"
        }
    }
}