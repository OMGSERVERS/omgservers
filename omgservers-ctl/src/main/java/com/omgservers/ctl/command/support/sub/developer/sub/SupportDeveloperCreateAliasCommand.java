package com.omgservers.ctl.command.support.sub.developer.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.support.developer.SupportDeveloperCreateAliasOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-alias",
        description = "Assign an alias to a developer.")
public class SupportDeveloperCreateAliasCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id of the developer to assign the alias to.")
    Long developerUserId;

    @CommandLine.Parameters(description = "Alias to assign to the developer.")
    String alias;

    @Inject
    SupportDeveloperCreateAliasOperation supportDeveloperCreateAliasOperation;

    @Override
    public void run() {
        supportDeveloperCreateAliasOperation.execute(developerUserId, alias, installation);
    }
}
