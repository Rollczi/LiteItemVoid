package dev.rollczi.liteitemvoid.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public final class LegacyColorProcessor implements UnaryOperator<Component> {

    private static final LegacyComponentSerializer AMPERSAND_SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder -> builder.match(Pattern.compile(".*"))
                .replacement((matchResult, builder1) -> AMPERSAND_SERIALIZER.deserialize(matchResult.group())));
    }
}
