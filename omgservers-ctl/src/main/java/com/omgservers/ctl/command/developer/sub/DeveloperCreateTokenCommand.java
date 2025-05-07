package com.omgservers.ctl.command.developer.sub;

import com.omgservers.ctl.command.ServiceCommand;
import com.omgservers.ctl.operation.command.developer.DeveloperCreateTokenOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-token",
        description = "Create developer token using credentials.")
public class DeveloperCreateTokenCommand extends ServiceCommand {

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String user;

    @CommandLine.Parameters(description = "Developer user password.")
    String password;

    @Inject
    DeveloperCreateTokenOperation developerCreateTokenOperation;

    @Override
    public void run() {
        developerCreateTokenOperation.execute(user, password, installation);
    }
}
