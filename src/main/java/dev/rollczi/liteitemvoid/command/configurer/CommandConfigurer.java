package dev.rollczi.liteitemvoid.command.configurer;

import dev.rollczi.litecommands.command.builder.CommandBuilder;
import dev.rollczi.litecommands.editor.Editor;
import dev.rollczi.litecommands.meta.Meta;
import dev.rollczi.litecommands.permission.PermissionSet;
import dev.rollczi.liteitemvoid.config.plugin.CommandConfig;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

public class CommandConfigurer implements Editor<CommandSender> {

    private final CommandConfig commandConfig;

    public CommandConfigurer(CommandConfig commandConfig) {
        this.commandConfig = commandConfig;
    }

    @Override
    public CommandBuilder<CommandSender> edit(CommandBuilder<CommandSender> context) {
        CommandConfig.Command command = this.commandConfig.commands.get(context.name());

        if (command == null) {
            return context;
        }

        for (Map.Entry<String, CommandConfig.CommandArgument> entry : command.arguments.entrySet()) {
            if (context.getChild(entry.getKey()).isEmpty()) {
                continue;
            }

            CommandConfig.CommandArgument argument = entry.getValue();

            context = context.editChild(entry.getKey(), child -> applyPermissions(child
                    .name(argument.name)
                    .aliases(argument.alias), argument.permissions));
        }

        return applyPermissions(context
                .name(command.name)
                .aliases(command.aliases), command.permissions);
    }

    private static CommandBuilder<CommandSender> applyPermissions(CommandBuilder<CommandSender> builder, List<String> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return builder.applyMeta(meta -> meta.put(Meta.PERMISSIONS, List.of()));
        }

        return builder.applyMeta(meta -> meta.put(Meta.PERMISSIONS, List.of(new PermissionSet(permissions))));
    }

}
