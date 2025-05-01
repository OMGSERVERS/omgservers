package com.omgservers.ctl.command.developer.sub.version.sub.util;

import com.omgservers.ctl.dto.image.ImageTypeEnum;

import java.util.Arrays;
import java.util.Iterator;

public class ImageTypeCandidates implements Iterable<String> {

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(ImageTypeEnum.values())
                .map(ImageTypeEnum::getType)
                .iterator();
    }
}
