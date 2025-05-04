package com.omgservers.ctl.command.developer.sub.project.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.project.DeveloperProjectCreateProjectOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-project",
        description = "Create a new project.")
public class DeveloperProjectCreateProjectCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant to which the project will be added.")
    String tenant;

    @Inject
    DeveloperProjectCreateProjectOperation developerProjectCreateProjectOperation;

    @Override
    public void run() {
        developerProjectCreateProjectOperation.execute(tenant, installation, user);
    }
}
