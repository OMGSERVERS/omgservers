package com.omgservers.ctl.command.support.sub.project.sub.util;

import com.omgservers.ctl.dto.permission.ProjectPermissionEnum;
import picocli.CommandLine;

public class ProjectPermissionConverter implements CommandLine.ITypeConverter<ProjectPermissionEnum> {

    @Override
    public ProjectPermissionEnum convert(String value) throws Exception {
        return ProjectPermissionEnum.fromString(value);
    }
}
