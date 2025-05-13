package com.omgservers.ctl.command.support.sub.project.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.support.project.SupportProjectCreateProjectOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-project",
        description = "Create a new project.")
public class SupportProjectCreateProjectCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant to which the project will be added.")
    String tenant;

    @Inject
    SupportProjectCreateProjectOperation supportProjectCreateProjectOperation;

    @Override
    public void run() {
        supportProjectCreateProjectOperation.execute(tenant, installation);
    }
}
