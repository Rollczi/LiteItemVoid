package dev.rollczi.liteitemvoid.command.configurer;

import dev.rollczi.litecommands.factory.CommandEditor;
import dev.rollczi.liteitemvoid.config.plugin.CommandConfig;

public class CommandConfigurer implements CommandEditor {

    private final CommandConfig commandConfig;

    public CommandConfigurer(CommandConfig commandConfig) {
        this.commandConfig = commandConfig;
    }

    @Override
    public State edit(State state) {
        CommandConfig.Command command = this.commandConfig.commands.get(state.getName());

        if (command == null) {
            return state;
        }

        for (String child : command.arguments.keySet()) {
            CommandConfig.CommandArgument argument = command.arguments.get(child);

            state = state.editChild(child, editor -> editor.name(argument.name)
                    .aliases(argument.alias, true)
                    .permission(argument.permissions, true));
        }

        return state.name(command.name)
                .aliases(command.aliases, true)
                .permission(command.permissions, true);

    }
}
