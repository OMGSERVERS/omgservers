package com.omgservers.ctl.command.support.sub.project.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.command.support.sub.project.sub.util.ProjectPermissionCandidates;
import com.omgservers.ctl.command.support.sub.project.sub.util.ProjectPermissionConverter;
import com.omgservers.ctl.dto.permission.ProjectPermissionEnum;
import com.omgservers.ctl.operation.command.support.project.SupportProjectDeletePermissionOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-permission",
        description = "Revoke a user's permission for a project.")
public class SupportProjectDeletePermissionCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project from which the permission will be revoked.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Project permission to revoke. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = ProjectPermissionConverter.class,
            completionCandidates = ProjectPermissionCandidates.class)
    ProjectPermissionEnum permission;

    @Inject
    SupportProjectDeletePermissionOperation supportProjectDeletePermissionOperation;

    @Override
    public void run() {
        supportProjectDeletePermissionOperation.execute(tenant,
                project,
                developer,
                permission,
                installation,
                user);
    }
}
