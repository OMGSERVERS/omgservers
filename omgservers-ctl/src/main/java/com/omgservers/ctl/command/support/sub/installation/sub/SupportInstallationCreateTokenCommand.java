package com.omgservers.ctl.command.support.sub.installation.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.support.SupportCreateTokenOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-token",
        description = "Create token using credentials.")
public class SupportInstallationCreateTokenCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Support user alias or id.")
    String user;

    @CommandLine.Parameters(description = "Support user password.")
    String password;

    @Inject
    SupportCreateTokenOperation supportCreateTokenOperation;

    @Override
    public void run() {
        supportCreateTokenOperation.execute(user, password, installation);
    }
}
