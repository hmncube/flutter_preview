import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.ChangelogSectionUrlBuilder
import org.jetbrains.changelog.date

fun properties(key: String) = project.findProperty(key).toString()

fun getFullVersion() = "${properties("appVersion")}-${properties("appPlatformVersion")}"


plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
    id("org.jetbrains.changelog") version "2.2.1"
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}

group = "com.hmncube"
version = "1.0.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1.7")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

changelog {
    groups.set(emptyList())
    repositoryUrl.set(properties("pluginRepositoryUrl"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        version = getFullVersion()
        sinceBuild.set(properties("pluginSinceBuild"))

        // Get the latest available change notes from the changelog file
        changeNotes.set(
            provider {
                with(changelog) {
                    renderItem(
                        getOrNull(properties("appVersion"))
                            ?: runCatching { getLatest() }.getOrElse { getUnreleased() },
                        Changelog.OutputType.HTML
                    )
                }
            }
        )
    }
/*
    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }*/

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        dependsOn("patchPluginXml")
        token.set(System.getenv("ORG_GRADLE_PROJECT_intellijPublishToken"))
        channels.set(listOf("default"))
    }

    register<GradleBuild>("ktlint") {
        tasks = listOf(
            "runKtlintFormatOverKotlinScripts",
            "runKtlintFormatOverMainSourceSet",
            "runKtlintFormatOverTestSourceSet"
        )
    }
}
