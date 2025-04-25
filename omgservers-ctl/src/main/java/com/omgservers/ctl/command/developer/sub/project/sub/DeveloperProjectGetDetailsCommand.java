package com.omgservers.ctl.command.developer.sub.project.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.project.DeveloperProjectGetDetailsOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "get-details",
        description = "Get details of a project.")
public class DeveloperProjectGetDetailsCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the project to get details.")
    String project;

    @Inject
    DeveloperProjectGetDetailsOperation developerProjectGetDetailsOperation;

    @Override
    public void run() {
        developerProjectGetDetailsOperation.execute(tenant, project, service, user, prettyPrint);
    }
}
