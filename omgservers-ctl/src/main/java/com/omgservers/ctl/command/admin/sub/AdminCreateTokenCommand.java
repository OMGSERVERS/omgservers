package com.omgservers.ctl.command.admin.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.admin.AdminCreateTokenOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-token",
        description = "Create admin token using credentials.")
public class AdminCreateTokenCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Admin user alias or id.")
    String user;

    @CommandLine.Parameters(description = "Admin user password.")
    String password;

    @Inject
    AdminCreateTokenOperation adminCreateTokenOperation;

    @Override
    public void run() {
        adminCreateTokenOperation.execute(user, password, installation);
    }
}
