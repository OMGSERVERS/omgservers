package com.omgservers.ctl.command.developer.sub.localtesting.sub;

import com.omgservers.ctl.operation.command.developer.localtesting.DeveloperLocalTestingInitTenantOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "init-tenant",
        description = "Initialize a new tenant for local testing.")
public class DeveloperLocalTestingInitTenantCommand implements Runnable {

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
    DeveloperLocalTestingInitTenantOperation developerLocalTestingInitTenantOperation;

    @Override
    public void run() {
        developerLocalTestingInitTenantOperation.execute(tenant, project, stage);
    }
}
