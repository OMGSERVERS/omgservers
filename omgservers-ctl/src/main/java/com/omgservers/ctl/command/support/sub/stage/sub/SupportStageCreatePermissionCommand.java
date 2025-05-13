package com.omgservers.ctl.command.support.sub.stage.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.command.support.sub.stage.sub.util.StagePermissionCandidates;
import com.omgservers.ctl.command.support.sub.stage.sub.util.StagePermissionConverter;
import com.omgservers.ctl.dto.permission.StagePermissionEnum;
import com.omgservers.ctl.operation.command.support.stage.SupportStageCreatePermissionOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-permission",
        description = "Grant a permission to a user for a stage.")
public class SupportStageCreatePermissionCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project that owns the stage.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the stage to which the permission will be granted.")
    String stage;

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Stage permission to grant. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = StagePermissionConverter.class,
            completionCandidates = StagePermissionCandidates.class)
    StagePermissionEnum permission;

    @Inject
    SupportStageCreatePermissionOperation supportStageCreatePermissionOperation;

    @Override
    public void run() {
        supportStageCreatePermissionOperation.execute(tenant,
                project,
                stage,
                developer,
                permission,
                installation);
    }
}
