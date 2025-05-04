package com.omgservers.ctl.command.support.sub.developer.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.support.developer.SupportDeveloperDeleteDeveloperOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-developer",
        description = "Delete a developer account by user id.")
public class SupportDeveloperDeleteDeveloperCommand extends UserCommand {

    @CommandLine.Parameters(description = "User id of the developer.")
    Long userId;

    @Inject
    SupportDeveloperDeleteDeveloperOperation supportDeveloperDeleteDeveloperOperation;

    @Override
    public void run() {
        supportDeveloperDeleteDeveloperOperation.execute(userId, installation, user);
    }
}
