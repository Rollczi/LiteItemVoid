/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.liteitemvoid.command.configurer.CommandConfigurer;
import dev.rollczi.liteitemvoid.command.contextual.AudienceContextual;
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
import dev.rollczi.liteitemvoid.view.ViewRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemVoidPlugin extends JavaPlugin {

    private ConfigurationManager configurationManager;

    private AudienceProvider audienceProvider;

    private DeepVoid deepVoid;
    private ViewRegistry viewRegistry;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        Scheduler scheduler = new SchedulerBukkit(this);
        this.audienceProvider = BukkitAudiences.create(this);

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.configurationManager.loadConfigs();

        NotificationService notificationService = new NotificationService(this.audienceProvider);

        this.deepVoid = new DeepVoid();
        this.viewRegistry = new ViewRegistry(this.getServer(), configurationManager.getPluginConfig());

        DeepVoidController deepVoidController = new DeepVoidController(audienceProvider, this.getServer(), configurationManager.getPluginConfig(), scheduler, deepVoid, viewRegistry.deepVoid);

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "deep-void")
                .invalidUsageHandler(new InvalidUseMessageHandler(configurationManager.getPluginConfig(), notificationService))
                .permissionHandler(new PermissionMessageHandler(notificationService, configurationManager.getPluginConfig()))

                .contextualBind(Player.class,   new BukkitOnlyPlayerContextual<>("Only players can use this command!"))
                .contextualBind(Audience.class, new AudienceContextual(audienceProvider))

                .typeBind(ConfigurationManager.class,   () -> this.configurationManager)
                .typeBind(PluginConfig.class,           () -> this.configurationManager.getPluginConfig())
                .typeBind(DeepVoid.class,               () -> this.deepVoid)
                .typeBind(ViewRegistry.class,           () -> this.viewRegistry)
                .typeBind(AudienceProvider.class,       () -> this.audienceProvider)
                .typeBind(PluginDescriptionFile.class,  this::getDescription)
                .command(DeepVoidCommand.class)

                .commandGlobalEditor(new CommandConfigurer(configurationManager.getCommandConfig()))

                .register();

        this.getServer().getPluginManager().registerEvents(new DeepVoidViewController(this.viewRegistry.deepVoid), this);
        deepVoidController.runClearTask();
    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.getPlatform().unregisterAll();
        }

        if (this.audienceProvider != null) {
            this.audienceProvider.close();
        }
    }

}
