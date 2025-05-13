package com.omgservers.ctl.command.support.sub.developer.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.support.developer.SupportDeveloperDeleteDeveloperOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-developer",
        description = "Delete a developer user by user id or alias.")
public class SupportDeveloperDeleteDeveloperCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @Inject
    SupportDeveloperDeleteDeveloperOperation supportDeveloperDeleteDeveloperOperation;

    @Override
    public void run() {
        supportDeveloperDeleteDeveloperOperation.execute(developer,
                installation);
    }
}
