/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.paper;

import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.LiteFactory;
import dev.rollczi.litecommands.bind.basic.OriginalSenderBind;
import dev.rollczi.litecommands.platform.LitePlatformManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class LitePaperFactory {

    private LitePaperFactory() {}

    public static <SENDER, P extends LitePlatformManager<SENDER>> LiteCommandsBuilder<CommandSender, LitePaperPlatformManager> builder(Server server, BukkitAudiences audiences, MiniMessage miniMessage, String fallbackPrefix) {
        return LiteFactory.<CommandSender, LitePaperPlatformManager>builder()
                .typeBind(Server.class, server)
                .typeBind(CommandSender.class, new OriginalSenderBind())
                .typeBind(MiniMessage.class, miniMessage)
                .platform(new LitePaperPlatformManager(server, miniMessage, fallbackPrefix, audiences));
    }

}
