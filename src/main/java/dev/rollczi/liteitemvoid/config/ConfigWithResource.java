/*
 * Copyright (c) 2022 Rollczi
 */

package dev.rollczi.liteitemvoid.config;

import net.dzikoysk.cdn.source.Resource;

public interface ConfigWithResource {

    Resource getResource();

    void setResource(Resource resource);

}
