/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.bind;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.bind.Parameter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;

public class AudienceBukkitBind implements Parameter<Audience> {

    private final BukkitAudiences bukkitAudiences;

    public AudienceBukkitBind(BukkitAudiences bukkitAudiences) {
        this.bukkitAudiences = bukkitAudiences;
    }

    @Override
    public Object apply(LiteInvocation invocation) {
        return bukkitAudiences.sender((CommandSender) invocation.sender().getSender());
    }

}
