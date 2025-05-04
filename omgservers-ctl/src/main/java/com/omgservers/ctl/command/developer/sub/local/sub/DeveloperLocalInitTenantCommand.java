package com.omgservers.ctl.command.developer.sub.local.sub;

import com.omgservers.ctl.operation.command.local.DeveloperLocalInitTenantOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "init-tenant",
        description = "Initialize a new tenant for local testing.")
public class DeveloperLocalInitTenantCommand implements Runnable {

    @CommandLine.Parameters(description = "Alias of the tenant to be created for local testing. Default is \"${DEFAULT-VALUE}\"",
            defaultValue = "omgservers")
    String tenant;

    @CommandLine.Parameters(description = "Alias of the project to be created for local testing. Default is \"${DEFAULT-VALUE}\"",
            defaultValue = "localtesting")
    String project;

    @CommandLine.Parameters(description = "Alias of the stage to be created for local testing. Default is \"${DEFAULT-VALUE}\"",
            defaultValue = "default")
    String stage;

    @Inject
    DeveloperLocalInitTenantOperation developerLocalInitTenantOperation;

    @Override
    public void run() {
        developerLocalInitTenantOperation.execute(tenant, project, stage);
    }
}
