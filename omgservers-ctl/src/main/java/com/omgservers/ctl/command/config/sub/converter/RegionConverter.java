package com.omgservers.ctl.command.config.sub.converter;

import com.omgservers.ctl.dto.region.RegionEnum;
import picocli.CommandLine;

public class RegionConverter implements CommandLine.ITypeConverter<RegionEnum> {

    @Override
    public RegionEnum convert(String value) throws Exception {
        return RegionEnum.fromString(value);
    }
}
