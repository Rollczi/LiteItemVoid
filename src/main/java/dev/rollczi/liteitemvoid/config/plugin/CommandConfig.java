package dev.rollczi.liteitemvoid.config.plugin;

import com.google.common.collect.ImmutableMap;
import dev.rollczi.liteitemvoid.config.AbstractConfigWithResource;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandConfig extends AbstractConfigWithResource {

    public CommandConfig(File file, String child) {
        super(file, child);
    }

    @Description( {
            "#PL: Sekcja konfiguracji komend. W tej sekcji możesz zmienić nazwy komend, argumentów i uprawnien:",
            "#EN: Section of command config. Set name of command, argument names and permissions:"
    })
    public Map<String, Command> commands = ImmutableMap.of(
            "deepvoid",

            new Command(
                    "deepvoid",

                    Arrays.asList("void", "otchlan"),

                    new ArrayList<>(),

                    ImmutableMap.of(
                            "help", new CommandArgument("help", Collections.singletonList("help"), Collections.singletonList("itemvoid.admin")),
                            "reload", new CommandArgument("reload", Collections.singletonList("reload"), Collections.singletonList("itemvoid.admin")),
                            "on", new CommandArgument("on", Collections.singletonList("on"), Collections.singletonList("itemvoid.admin")),
                            "off", new CommandArgument("off", Collections.singletonList("off"), Collections.singletonList("itemvoid.admin")),
                            "clear", new CommandArgument("clear", Collections.singletonList("clear"), Collections.singletonList("itemvoid.admin")),
                            "force-clear", new CommandArgument("force-clear", Collections.singletonList("force-clear"), Collections.singletonList("itemvoid.admin")),
                            "version", new CommandArgument("version", Collections.singletonList("version"), Collections.singletonList("itemvoid.admin")),
                            "info", new CommandArgument("info", Collections.singletonList("info"), Collections.singletonList("itemvoid.admin"))
                    )
            )
    );

    @Contextual
    public static class Command {

        public String name;
        public List<String> aliases;
        public List<String> permissions;
        public Map<String, CommandArgument> arguments = new HashMap<>();

        public Command(String name, List<String> aliases, List<String> permissions, Map<String, CommandArgument> arguments) {
            this.name = name;
            this.aliases = aliases;
            this.permissions = permissions;
            this.arguments = arguments;
        }

        public Command(String name, List<String> aliases, List<String> permissions) {
            this.name = name;
            this.aliases = aliases;
            this.permissions = permissions;
        }

        public Command() {

        }
    }

    @Contextual
    public static class CommandArgument {

        public String name;
        public List<String> alias;
        public List<String> permissions;

        public CommandArgument(String name, List<String> alias) {
            this.name = name;
            this.alias = alias;
        }

        public CommandArgument(String name, List<String> alias, List<String> permissions) {
            this.name = name;
            this.alias = alias;
            this.permissions = permissions;
        }

        public CommandArgument() {

        }
    }


}
