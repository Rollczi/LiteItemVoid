/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.view;

import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidView;
import org.bukkit.Server;

public class ViewRegistry {

    public final DeepVoidView deepVoid;

    public ViewRegistry(Server server, PluginConfig pluginConfig) {
        this.deepVoid = new DeepVoidView(pluginConfig, server);
    }

}
