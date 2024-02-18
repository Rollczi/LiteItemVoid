import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("xyz.jpenilla.run-paper") version "2.2.0"
}

group = "dev.rollczi"
version = "2.0.4"

repositories {
    mavenCentral()

    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }

    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.mattstudios.me/artifactory/public/") }
}

dependencies {
    // Spigot API
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")

    // kyori adventure
    implementation("net.kyori:adventure-platform-bukkit:4.3.0")
    implementation("net.kyori:adventure-text-minimessage:4.13.0")

    // LiteCommands
    implementation("dev.rollczi.litecommands:bukkit-adventure:2.8.8")

    // TriumphGUI
    implementation("dev.triumphteam:triumph-gui:3.1.4")

    // CDN
    implementation("net.dzikoysk:cdn:1.14.4") {
        exclude("kotlin")
    }
}

tasks.withType<JavaCompile> {
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

tasks.withType<ShadowJar> {
    archiveFileName.set("LiteItemVoid-${project.version}-build.jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "org/checkerframework/**",
        "META-INF/**",
        "javax/**"
    )

    mergeServiceFiles()
    minimize()

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