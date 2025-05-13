package com.omgservers.ctl.operation.command.support.stage;

import com.omgservers.ctl.dto.permission.StagePermissionEnum;

public interface SupportStageDeletePermissionOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String developer,
                 StagePermissionEnum permission,
                 String installation);
}
