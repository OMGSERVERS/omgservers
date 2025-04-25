package com.omgservers.ctl.command.admin.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.admin.AdminBcryptHashOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "bcrypt-hash",
        description = "Generate a bcrypt hash of the given value.")
public class AdminBcryptHashCommand extends UserCommand {

    @CommandLine.Parameters(description = "Value to be encrypted.")
    String value;

    @Inject
    AdminBcryptHashOperation adminBcryptHashOperation;

    @Override
    public void run() {
        adminBcryptHashOperation.execute(value, service, user);
    }
}
