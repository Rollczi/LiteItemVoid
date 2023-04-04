package dev.rollczi.liteitemvoid.config.composer;

import net.dzikoysk.cdn.serdes.Composer;
import net.dzikoysk.cdn.serdes.SimpleDeserializer;
import net.dzikoysk.cdn.serdes.SimpleSerializer;
import panda.std.Result;

import java.text.SimpleDateFormat;

public class DateFormatComposer implements Composer<SimpleDateFormat>, SimpleDeserializer<SimpleDateFormat>, SimpleSerializer<SimpleDateFormat> {

    @Override
    public Result<SimpleDateFormat, Exception> deserialize(String source) {
        return Result.<SimpleDateFormat, Exception>supplyThrowing(IllegalArgumentException.class, () -> new SimpleDateFormat(source))
                .orElse(error -> Result.ok(new SimpleDateFormat("mm:ss:SS")))
                .onError(Throwable::printStackTrace);
    }

    @Override
    public Result<String, Exception> serialize(SimpleDateFormat entity) {
        return Result.ok(entity.toPattern());
    }

}
