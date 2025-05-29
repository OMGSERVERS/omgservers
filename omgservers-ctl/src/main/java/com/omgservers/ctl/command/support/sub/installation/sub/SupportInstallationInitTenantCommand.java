package com.omgservers.ctl.command.support.sub.installation.sub;

import com.omgservers.ctl.command.InstallationCommand;
import com.omgservers.ctl.operation.command.support.installation.SupportInstallationInitTenantOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "init-tenant",
        description = "Initialize a brand new tenant.")
public class SupportInstallationInitTenantCommand extends InstallationCommand {

    @CommandLine.Parameters(description = "Alias of the developer to be created")
    String developer;

    @CommandLine.Parameters(description = "Alias of the tenant to be created.")
    String tenant;

    @CommandLine.Parameters(description = "Alias of the project to be created")
    String project;

    @CommandLine.Parameters(description = "Alias of the stage to be created. Default is \"${DEFAULT-VALUE}\"",
            defaultValue = "default")
    String stage;

    @Inject
    SupportInstallationInitTenantOperation supportInstallationInitTenantOperation;

    @Override
    public void run() {
        supportInstallationInitTenantOperation.execute(developer,
                tenant,
                project,
                stage,
                installation);
    }
}
