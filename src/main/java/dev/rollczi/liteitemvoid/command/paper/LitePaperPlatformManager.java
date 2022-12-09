/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.paper;

import dev.rollczi.litecommands.bukkit.LiteBukkitPlatformManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;

public class LitePaperPlatformManager extends LiteBukkitPlatformManager {

    private final BukkitAudiences audiences;

    public LitePaperPlatformManager(Server server, MiniMessage miniMessage, String fallbackPrefix, BukkitAudiences audiences) {
        super(server, fallbackPrefix);
        this.audiences = audiences;
        this.liteSenderCreator = originalSender -> new LitePaperSender(miniMessage, audiences.sender(originalSender), originalSender);
    }

}
