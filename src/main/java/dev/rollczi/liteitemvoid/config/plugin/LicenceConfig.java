/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config.plugin;


import net.dzikoysk.cdn.entity.Description;
import dev.rollczi.liteitemvoid.config.AbstractConfigWithResource;

import java.io.File;

public class LicenceConfig extends AbstractConfigWithResource {

    public LicenceConfig(File folder, String child) {
        super(folder, child);
    }

    @Description( {
            "#PL: Licencja - Jeśli kupiłeś ten produkt to proszę odwiedź naszego discorda w celu odebrania licencji: https://discord.com/invite/DFpmMJQsa4",
            "#EN: Licence - If you have purchased this product, please visit our discord to receive your license: https://discord.com/invite/DFpmMJQsa4"
    } )
    public String licence = "licence";

}
