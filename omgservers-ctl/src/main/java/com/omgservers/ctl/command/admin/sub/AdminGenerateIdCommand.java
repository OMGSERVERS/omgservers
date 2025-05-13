package com.omgservers.ctl.command.admin.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.admin.AdminGenerateIdOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "generate-id",
        description = "Generate a unique identifier.")
public class AdminGenerateIdCommand extends InstallationCommand {

    @Inject
    AdminGenerateIdOperation adminGenerateIdOperation;

    @Override
    public void run() {
        adminGenerateIdOperation.execute(installation);
    }
}
