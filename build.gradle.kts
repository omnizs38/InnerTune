plugins {
    alias(libs.plugins.hilt) apply (false)
    alias(libs.plugins.kotlin.ksp) apply (false)
    alias(libs.plugins.compose.compiler) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
    dependencies {
        classpath(libs.gradle)
        classpath(kotlin("gradle-plugin", libs.versions.kotlin.get()))
    }
}

tasks.register<Delete>("Clean") {
    delete(rootProject.layout.buildDirectory)
}

subprojects {
    if (project.findProperty("enableComposeCompilerReports") == "true") {
        val metricsDir = project.layout.buildDirectory.dir("compose_metrics")
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            compilerOptions {
                freeCompilerArgs.addAll(
                    metricsDir.map { dir ->
                        listOf("reports", "metrics").flatMap {
                            listOf(
                                "-P",
                                "plugin:androidx.compose.compiler.plugins.kotlin:${it}Destination=${dir.asFile.absolutePath}"
                            )
                        }
                    }
                )
            }
        }
    }
}
