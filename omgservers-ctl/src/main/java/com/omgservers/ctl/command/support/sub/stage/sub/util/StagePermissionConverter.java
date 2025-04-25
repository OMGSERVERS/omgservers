package com.omgservers.ctl.command.support.sub.stage.sub.util;

import com.omgservers.ctl.dto.permission.StagePermissionEnum;
import picocli.CommandLine;

public class StagePermissionConverter implements CommandLine.ITypeConverter<StagePermissionEnum> {

    @Override
    public StagePermissionEnum convert(String value) throws Exception {
        return StagePermissionEnum.fromString(value);
    }
}
