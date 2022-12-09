/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.command.bind;

import dev.rollczi.litecommands.LiteInvocation;
import dev.rollczi.litecommands.bind.Parameter;
import dev.rollczi.litecommands.valid.ValidationCommandException;
import dev.rollczi.litecommands.valid.ValidationInfo;
import org.bukkit.entity.Player;

public class PlayerBind implements Parameter<Player> {

    @Override
    public Object apply(LiteInvocation invocation) {
        Object sender = invocation.sender().getSender();

        if (!(sender instanceof Player)) {
            throw new ValidationCommandException(ValidationInfo.CUSTOM, "&cKomenda tylko dla graczy!");
        }

        return sender;

    }

}
