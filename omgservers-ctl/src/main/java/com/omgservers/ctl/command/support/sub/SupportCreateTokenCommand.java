package com.omgservers.ctl.command.support.sub;

import com.omgservers.ctl.command.ServiceCommand;
import com.omgservers.ctl.operation.command.support.SupportCreateTokenOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-token",
        description = "Create support token using credentials.")
public class SupportCreateTokenCommand extends ServiceCommand {

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
