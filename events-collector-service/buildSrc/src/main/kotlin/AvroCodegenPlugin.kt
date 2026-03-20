import org.apache.avro.tool.SpecificCompilerTool
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Property
import org.gradle.api.tasks.SourceSetContainer

@Suppress("unused")
class AvroCodegenPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply(JavaPlugin::class.java)

        val extension = project.extensions.create("avro", AvroProperty::class.java)

        extension.schemasDir.convention("src/main/resources/avro")
        extension.outputDir.convention("build/generated/avro")

        val generateAvroTask = project.tasks.register("generateAvro") {
            group = "build"
            description = "Generate Java Classes from AVSC Schema"

            val schemasDir = extension.schemasDir.map { project.file(it) }
            val outputDir = extension.outputDir.map { project.file(it) }

            inputs.dir(schemasDir)
            outputs.dir(outputDir)

            doLast {
                outputDir.get().mkdirs()

                SpecificCompilerTool().run(
                    System.`in`,
                    System.out,
                    System.err,
                    listOf(
                        "-encoding", "UTF-8",
                        "-string",
                        "-fieldVisibility", "private",
                        "-noSetters",
                        "schema",
                        schemasDir.get().absolutePath,
                        outputDir.get().absolutePath
                    )
                )
            }
        }

        project.extensions.getByType(SourceSetContainer::class.java).apply {
            named("main") {
                java.srcDir(extension.outputDir)
            }
        }

        project.tasks.named("compileJava") {
            dependsOn(generateAvroTask)
        }
    }
}

open class AvroProperty(project: Project) {
    val schemasDir: Property<String> = project.objects.property(String::class.java)
    val outputDir: Property<String> = project.objects.property(String::class.java)
}