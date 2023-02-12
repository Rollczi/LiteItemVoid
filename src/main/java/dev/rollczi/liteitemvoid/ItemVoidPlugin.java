/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid;

import dev.rollczi.litecommands.LiteCommands;
import org.bukkit.plugin.PluginDescriptionFile;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidCommand;
import dev.rollczi.liteitemvoid.command.bind.AudienceBukkitBind;
import dev.rollczi.liteitemvoid.command.message.InvalidUseMessageHandler;
import dev.rollczi.liteitemvoid.command.message.PermissionMessageHandler;
import dev.rollczi.liteitemvoid.command.paper.LegacyColorProcessor;
import dev.rollczi.liteitemvoid.command.paper.LitePaperFactory;
import dev.rollczi.liteitemvoid.config.ConfigurationManager;
import dev.rollczi.liteitemvoid.command.bind.PlayerBind;
import dev.rollczi.liteitemvoid.config.plugin.PluginConfig;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoid;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidController;
import dev.rollczi.liteitemvoid.deepvoid.DeepVoidViewController;
import dev.rollczi.liteitemvoid.scheduler.Scheduler;
import dev.rollczi.liteitemvoid.scheduler.SchedulerBukkit;
import dev.rollczi.liteitemvoid.view.ViewRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemVoidPlugin extends JavaPlugin {

    private Scheduler scheduler;
    private BukkitAudiences audience;
    private MiniMessage miniMessage;
    private ConfigurationManager configurationManager;
    private DeepVoid deepVoid;
    private DeepVoidController deepVoidController;
    private ViewRegistry viewRegistry;
    private LiteCommands liteCommands;

    @Override
    public void onEnable() {
        this.scheduler = new SchedulerBukkit(this);
        this.audience = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
                .postProcessor(new LegacyColorProcessor())
                .build();

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadConfigs();

        this.deepVoid = new DeepVoid();
        this.viewRegistry = new ViewRegistry(this.getServer(), configurationManager.getPluginConfig());
        this.deepVoidController = new DeepVoidController(audience, this.getServer(), configurationManager.getPluginConfig(), scheduler, deepVoid, viewRegistry.VOID);

        this.liteCommands = LitePaperFactory.builder(this.getServer(), audience, this.miniMessage, "deep-void")
                .invalidUseMessage(new InvalidUseMessageHandler(configurationManager.getPluginConfig()))
                .permissionMessage(new PermissionMessageHandler(configurationManager.getPluginConfig()))

                .parameterBind(Player.class, new PlayerBind())
                .parameterBind(Audience.class, new AudienceBukkitBind(audience))

                .typeBind(ConfigurationManager.class, () -> this.configurationManager)
                .typeBind(PluginConfig.class,         () -> this.configurationManager.getPluginConfig())
                .typeBind(DeepVoid.class,             () -> this.deepVoid)
                .typeBind(ViewRegistry.class,         () -> this.viewRegistry)
                .typeBind(AudienceProvider.class,     () -> this.audience)
                .typeBind(PluginDescriptionFile.class, this::getDescription)
                .command(DeepVoidCommand.class)
                .register();

        this.getServer().getPluginManager().registerEvents(new DeepVoidViewController(this.viewRegistry.VOID), this);
        this.deepVoidController.runClearTask();
    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.getPlatformManager().unregisterCommands();
        }

        if (this.audience != null) {
            this.audience.close();
        }
    }

}
