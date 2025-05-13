package com.omgservers.ctl.command.support.sub.project.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.support.project.SupportProjectDeleteProjectOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-project",
        description = "Delete a project by id or alias.")
public class SupportProjectDeleteProjectCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project to be deleted.")
    String project;

    @Inject
    SupportProjectDeleteProjectOperation supportProjectDeleteProjectOperation;

    @Override
    public void run() {
        supportProjectDeleteProjectOperation.execute(tenant, project, installation);
    }
}
