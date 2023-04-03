package dev.rollczi.liteitemvoid;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class NotificationService {

    private final AudienceProvider audienceProvider;

    public NotificationService(AudienceProvider audienceProvider) {
        this.audienceProvider = audienceProvider;
    }

    public void sendMessage(CommandSender commandSender, Component message) {
        this.toAudience(commandSender).sendMessage(message);
    }

    private Audience toAudience(CommandSender commandSender) {
        if (commandSender instanceof Player) {
            return audienceProvider.player(((Player) commandSender).getUniqueId());
        }

        return audienceProvider.console();
    }
}
