/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.view;

import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidView;
import org.bukkit.Server;

public class ViewRegistry {

    public final DeepVoidView VOID;

    public ViewRegistry(Server server, PluginConfig pluginConfig) {
        VOID = new DeepVoidView(pluginConfig, server);
    }

}
