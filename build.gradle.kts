plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "dev.rollczi"
version = "2.0.5-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    // Spigot API
    compileOnly("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")

    // kyori adventure
    implementation("net.kyori:adventure-platform-bukkit:4.3.2")
    implementation("net.kyori:adventure-text-minimessage:4.16.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit-adventure:2.8.9")

    // TriumphGUI
    implementation("dev.triumphteam:triumph-gui:3.1.7")

    // CDN
    implementation("net.dzikoysk:cdn:1.14.4") {
        exclude("kotlin")
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkit {
    main = "dev.rollczi.liteitemvoid.ItemVoidPlugin"
    apiVersion = "1.13"
    prefix = "LiteItemVoid"
    authors = listOf("Rollczi", "Piotrulla")
    name = "LiteItemVoid"
    version = "${project.version}"
}

tasks.shadowJar {
    archiveFileName.set("LiteItemVoid-${project.version}-build.jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**"
    )

    mergeServiceFiles()

    val prefix = "dev.rollczi.liteitemvoid.libs"

    listOf(
        "panda",
        "org.panda_lang",
        "net.dzikoysk",
        "net.kyori",
        "dev.rollczi.litecommands",
        "com.google.gson"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}

tasks.runServer {
    minecraftVersion("1.20.4")
}