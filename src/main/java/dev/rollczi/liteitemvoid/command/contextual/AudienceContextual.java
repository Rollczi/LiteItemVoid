/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.contextual;

import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Result;

public class AudienceContextual implements Contextual<CommandSender, Audience> {

    private final AudienceProvider audienceProvider;

    public AudienceContextual(AudienceProvider audienceProvider) {
        this.audienceProvider = audienceProvider;
    }

    @Override
    public Result<Audience, ?> extract(CommandSender commandSender, Invocation<CommandSender> invocation) {
        if (commandSender instanceof Player) {
            return Result.ok(this.audienceProvider.player(((Player) commandSender).getUniqueId()));
        }

        return Result.ok(this.audienceProvider.console());
    }
}
