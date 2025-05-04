package com.omgservers.ctl.command.developer.sub.project.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.project.DeveloperProjectCreateAliasOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-alias",
        description = "Assign an alias to a project.")
public class DeveloperProjectCreateAliasCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant that owns the project.")
    String tenant;

    @CommandLine.Parameters(description = "Id of the project to assign the alias to.")
    Long projectId;

    @CommandLine.Parameters(description = "Alias to assign to the project.")
    String alias;

    @Inject
    DeveloperProjectCreateAliasOperation developerProjectCreateAliasOperation;

    @Override
    public void run() {
        developerProjectCreateAliasOperation.execute(tenant,
                projectId,
                alias,
                installation,
                user);
    }
}
