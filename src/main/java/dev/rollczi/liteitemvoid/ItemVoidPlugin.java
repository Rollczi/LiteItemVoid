/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.adventure.LiteAdventureExtension;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.liteitemvoid.command.configurer.CommandConfigurer;
import dev.rollczi.liteitemvoid.command.message.InvalidUseMessageHandler;
import dev.rollczi.liteitemvoid.command.message.PermissionMessageHandler;
import dev.rollczi.liteitemvoid.config.ConfigurationManager;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoid;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidCommand;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidController;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidViewController;
import dev.rollczi.liteitemvoid.scheduler.Scheduler;
import dev.rollczi.liteitemvoid.scheduler.SchedulerBukkit;
import dev.rollczi.liteitemvoid.util.LegacyColorProcessor;
import dev.rollczi.liteitemvoid.view.ViewRegistry;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemVoidPlugin extends JavaPlugin {

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        Scheduler scheduler = new SchedulerBukkit(this);
        MiniMessage miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();

        ConfigurationManager configurationManager = new ConfigurationManager(this.getDataFolder());
        configurationManager.loadConfigs();

        PluginConfig pluginConfig = configurationManager.getPluginConfig();
        DeepVoid deepVoid = new DeepVoid();
        ViewRegistry viewRegistry = new ViewRegistry(this.getServer(), pluginConfig);

        DeepVoidController deepVoidController = new DeepVoidController(this.getServer(), pluginConfig, scheduler, deepVoid, viewRegistry.deepVoid);

        this.liteCommands = LiteBukkitFactory.builder("lite-item-void", this)
                .extension(new LiteAdventureExtension<>(), settings -> settings
                        .miniMessage(true)
                        .legacyColor(true)
                        .serializer(miniMessage))

                .invalidUsage(new InvalidUseMessageHandler(pluginConfig))
                .missingPermission(new PermissionMessageHandler(pluginConfig))
                .message(LiteBukkitMessages.PLAYER_ONLY, "Only players can use this command!")

                .editorGlobal(new CommandConfigurer(configurationManager.getCommandConfig()))
                .commands(new DeepVoidCommand(pluginConfig, deepVoid, viewRegistry, configurationManager, this.getPluginMeta(), miniMessage, this.getLogger()))

                .build();

        this.getServer().getPluginManager().registerEvents(new DeepVoidViewController(viewRegistry.deepVoid), this);
        deepVoidController.runClearTask();
    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}
