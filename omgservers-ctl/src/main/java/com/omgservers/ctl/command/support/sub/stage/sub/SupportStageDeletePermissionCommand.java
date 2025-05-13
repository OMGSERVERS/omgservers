package com.omgservers.ctl.command.support.sub.stage.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.command.support.sub.stage.sub.util.StagePermissionCandidates;
import com.omgservers.ctl.command.support.sub.stage.sub.util.StagePermissionConverter;
import com.omgservers.ctl.dto.permission.StagePermissionEnum;
import com.omgservers.ctl.operation.command.support.stage.SupportStageDeletePermissionOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-permission",
        description = "Revoke a user's permission for a stage.")
public class SupportStageDeletePermissionCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the stage.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage from which the permission will be revoked.")
    String stage;

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Stage permission to revoke. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = StagePermissionConverter.class,
            completionCandidates = StagePermissionCandidates.class)
    StagePermissionEnum permission;

    @Inject
    SupportStageDeletePermissionOperation supportStageDeletePermissionOperation;

    @Override
    public void run() {
        supportStageDeletePermissionOperation.execute(tenant,
                project,
                stage,
                developer,
                permission,
                installation);
    }
}
