import xyz.jpenilla.runpaper.task.RunServer

plugins {
    java
    checkstyle
    idea
    alias(libs.plugins.paper.userdev)
    alias(libs.plugins.paper.run)
    alias(libs.plugins.spotless)
    alias(libs.plugins.shadow)
    `maven-publish`
}

group = "me.machinemaker"
version = "0.7.1-STRIPPED"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.minecraft.map { "$it-R0.1-SNAPSHOT" })
    implementation(libs.mirror)
    implementation(libs.reflectionRemapper)

    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

checkstyle {
    configDirectory.set(rootProject.file(".checkstyle"))
    isShowViolations = true
    toolVersion = "10.12.5"
}

spotless {
    java {
        licenseHeaderFile(file("HEADER"))
    }
}

publishing {
    repositories {
        maven {
            name = "releases"
            credentials {
                username = providers.gradleProperty("shardNexusUser").get()
                password = providers.gradleProperty("shardNexusPassword").get()
            }
            url = uri("https://repo.projectshard.dev/repository/maven-cn-releases/")
        }
        maven {
            name = "snapshots"
            credentials {
                username = providers.gradleProperty("shardNexusUser").get()
                password = providers.gradleProperty("shardNexusPassword").get()
            }
            url = uri("https://repo.projectshard.dev/repository/maven-cn-snapshots/")
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.MOJANG_PRODUCTION

tasks {
    assemble {
        dependsOn(shadowJar)
    }

    shadowJar {
        isEnableRelocation = true
        relocationPrefix = "me.machinemaker.treasuremapsplus.libs"
    }

    compileJava {
        options.release = 21
        options.encoding = Charsets.UTF_8.toString()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.toString()
        filesMatching("paper-plugin.yml") {
            expand("version" to version)
        }
    }

    test {
        useJUnitPlatform()
    }

    withType<RunServer> { // set for both runServer and runMojangMappedServer
        systemProperty("com.mojang.eula.agree", "true")

        downloadPlugins {
            url("https://download.luckperms.net/1543/bukkit/loader/LuckPerms-Bukkit-5.4.130.jar")
        }
    }

    runServer {
        minecraftVersion(libs.versions.minecraft.get())
    }

    create("printVersion") {
        doFirst {
            println(version)
        }
    }
}
