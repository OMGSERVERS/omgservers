package com.omgservers.ctl.command.developer.sub.deployment.sub.util;

import com.omgservers.ctl.dto.image.ImageTypeEnum;
import picocli.CommandLine;

public class ImageTypeConverter implements CommandLine.ITypeConverter<ImageTypeEnum> {

    @Override
    public ImageTypeEnum convert(String value) throws Exception {
        return ImageTypeEnum.fromString(value);
    }
}
