package com.omgservers.ctl.command.ctl.sub.installation.sub.util;

import com.omgservers.ctl.dto.region.RegionEnum;
import picocli.CommandLine;

public class RegionConverter implements CommandLine.ITypeConverter<RegionEnum> {

    @Override
    public RegionEnum convert(String value) throws Exception {
        return RegionEnum.fromString(value);
    }
}
