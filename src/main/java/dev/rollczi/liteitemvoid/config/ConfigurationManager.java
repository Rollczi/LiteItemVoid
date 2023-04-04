/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config;

import dev.rollczi.liteitemvoid.config.composer.ComponentComposer;
import dev.rollczi.liteitemvoid.config.composer.DateFormatComposer;
import dev.rollczi.liteitemvoid.config.composer.MaterialComposer;
import dev.rollczi.liteitemvoid.config.plugin.CommandConfig;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import org.bukkit.Material;
import net.dzikoysk.cdn.Cdn;
import net.dzikoysk.cdn.CdnException;
import net.dzikoysk.cdn.CdnFactory;
import net.kyori.adventure.text.Component;
import panda.std.Blank;
import panda.std.Result;

import java.io.File;
import java.text.SimpleDateFormat;

public class ConfigurationManager {

    private final Cdn cdn = CdnFactory
            .createYamlLike()
            .getSettings()
            .withComposer(Component.class, new ComponentComposer())
            .withComposer(Material.class, new MaterialComposer())
            .withComposer(SimpleDateFormat.class, new DateFormatComposer())
            .build();

    private final PluginConfig pluginConfig;
    private final CommandConfig commandConfig;

    public ConfigurationManager(File dataFolder) {
        this.pluginConfig = new PluginConfig(dataFolder, "config.yml");
        this.commandConfig = new CommandConfig(dataFolder, "commands.yml");
    }

    public Result<Blank, CdnException> loadConfigs() {
        return Result.<CdnException>ok()
                .flatMap(blank -> this.load(this.pluginConfig))
                .flatMap(blank -> this.load(this.commandConfig))
                .mapToBlank();
    }

    public <T extends ConfigWithResource> Result<T, CdnException> load(T config) {
        return cdn.load(config.getResource(), config)
                .flatMap(load -> cdn.render(config, config.getResource()))
                .map(render -> config);
    }

    public <T extends ConfigWithResource> void save(T config) {
        cdn.render(config, config.getResource());
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public CommandConfig getCommandConfig() {
        return commandConfig;
    }

}
