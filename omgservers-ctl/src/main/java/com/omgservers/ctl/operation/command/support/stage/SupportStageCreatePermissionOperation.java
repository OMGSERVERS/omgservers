package com.omgservers.ctl.operation.command.support.stage;

import com.omgservers.ctl.dto.permission.StagePermissionEnum;

public interface SupportStageCreatePermissionOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String developer,
                 StagePermissionEnum permission,
                 String service,
                 String user);
}
