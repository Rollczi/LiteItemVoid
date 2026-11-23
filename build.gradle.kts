plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.6.1"
    id("de.eldoria.plugin-yml.bukkit") version "0.9.0"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "dev.rollczi"
version = "2.1.0"

repositories {
    mavenCentral()

    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Paper API (Adventure and MiniMessage are provided natively by Paper)
    compileOnly("io.papermc.paper:paper-api:26.3.build.259-stable")

    // LiteCommands
    implementation("dev.rollczi:litecommands-bukkit:3.11.0")
    implementation("dev.rollczi:litecommands-adventure:3.11.0")

    // CDN
    implementation("net.dzikoysk:cdn:1.14.9") {
        exclude("kotlin")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
    options.release = 25
}

bukkit {
    main = "dev.rollczi.liteitemvoid.ItemVoidPlugin"
    apiVersion = "26.3"
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

    dependencies {
        exclude(dependency("net.kyori:.*"))
    }

    mergeServiceFiles()

    val prefix = "dev.rollczi.liteitemvoid.libs"

    listOf(
        "panda",
        "org.panda_lang",
        "net.dzikoysk",
        "dev.rollczi.litecommands"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}

tasks.runServer {
    minecraftVersion("26.3")
    javaLauncher.set(javaToolchains.launcherFor { languageVersion.set(JavaLanguageVersion.of(25)) })
}
