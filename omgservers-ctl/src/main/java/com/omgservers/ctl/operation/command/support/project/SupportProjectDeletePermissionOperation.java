package com.omgservers.ctl.operation.command.support.project;

import com.omgservers.ctl.dto.permission.ProjectPermissionEnum;

public interface SupportProjectDeletePermissionOperation {

    void execute(String tenant,
                 String project,
                 String developer,
                 ProjectPermissionEnum permission,
                 String installation);
}
