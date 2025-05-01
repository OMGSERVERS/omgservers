package com.omgservers.ctl.command.developer.sub.project.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.project.DeveloperProjectDeleteProjectOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-project",
        description = "Delete a project by id or alias.")
public class DeveloperProjectDeleteProjectCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project to be deleted.")
    String project;

    @Inject
    DeveloperProjectDeleteProjectOperation developerProjectDeleteProjectOperation;

    @Override
    public void run() {
        developerProjectDeleteProjectOperation.execute(tenant, project, service, user);
    }
}
