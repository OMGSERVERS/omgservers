package com.omgservers.ctl.command.developer.sub.installation.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.developer.installation.DeveloperInstallationCreateTokenOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-token",
        description = "Create developer token using credentials.")
public class DeveloperInstallationCreateTokenCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Developer user password.")
    String password;

    @Inject
    DeveloperInstallationCreateTokenOperation developerInstallationCreateTokenOperation;

    @Override
    public void run() {
        developerInstallationCreateTokenOperation.execute(developer,
                password,
                installation);
    }
}
