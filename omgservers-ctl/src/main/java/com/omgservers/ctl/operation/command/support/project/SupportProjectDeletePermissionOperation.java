package com.omgservers.ctl.operation.command.support.project;

import com.omgservers.ctl.dto.permission.ProjectPermissionEnum;

public interface SupportProjectDeletePermissionOperation {

    void execute(String tenant,
                 String project,
                 Long userId,
                 ProjectPermissionEnum permission,
                 String service,
                 String user);
}
