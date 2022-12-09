/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.paper;

import dev.rollczi.litecommands.platform.LiteSender;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public class LitePaperSender implements LiteSender {

    private final MiniMessage miniMessage;
    private final Audience audience;
    private final CommandSender commandSender;

    public LitePaperSender(MiniMessage miniMessage, Audience audience, CommandSender commandSender) {
        this.miniMessage = miniMessage;
        this.audience = audience;
        this.commandSender = commandSender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        this.audience.sendMessage(miniMessage.deserialize(message));
    }

    @Override
    public Object getSender() {
        return commandSender;
    }

}
