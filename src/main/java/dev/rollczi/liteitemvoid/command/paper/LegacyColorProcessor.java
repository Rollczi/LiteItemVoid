package dev.rollczi.liteitemvoid.command.paper;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class LegacyColorProcessor implements UnaryOperator<Component>  {

    private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder -> builder.match(Pattern.compile(".*"))
            .replacement((matchResult, builder1) -> serializer.deserialize((matchResult.group()))));
    }

}
