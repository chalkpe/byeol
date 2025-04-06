import kr.entree.spigradle.kotlin.bStats
import kr.entree.spigradle.kotlin.spigot

plugins {
    kotlin("jvm") version "2.0.0"
    id("kr.entree.spigradle") version "2.4.3"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "pe.chalk.bukkit"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(spigot("1.20.6"))
    implementation(bStats("3.0.2"))
}

spigot {
    description = "Byeol"
    commands {
        create("nickname") {
            aliases = listOf("닉네임")
        }
    }
}

tasks.shadowJar {
    archiveClassifier.set("")
    relocate("org.bstats", "pe.chalk.bukkit.bstats")
}