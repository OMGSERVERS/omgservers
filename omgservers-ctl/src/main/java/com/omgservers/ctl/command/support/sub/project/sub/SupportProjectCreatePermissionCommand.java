package com.omgservers.ctl.command.support.sub.project.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.command.support.sub.project.sub.util.ProjectPermissionCandidates;
import com.omgservers.ctl.command.support.sub.project.sub.util.ProjectPermissionConverter;
import com.omgservers.ctl.dto.permission.ProjectPermissionEnum;
import com.omgservers.ctl.operation.command.support.project.SupportProjectCreatePermissionOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-permission",
        description = "Grant a permission to a user for a project.")
public class SupportProjectCreatePermissionCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project to which the permission will be granted.")
    String project;

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Project permission to grant. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = ProjectPermissionConverter.class,
            completionCandidates = ProjectPermissionCandidates.class)
    ProjectPermissionEnum permission;

    @Inject
    SupportProjectCreatePermissionOperation supportProjectCreatePermissionOperation;

    @Override
    public void run() {
        supportProjectCreatePermissionOperation.execute(tenant,
                project,
                developer,
                permission,
                installation,
                user);
    }
}
