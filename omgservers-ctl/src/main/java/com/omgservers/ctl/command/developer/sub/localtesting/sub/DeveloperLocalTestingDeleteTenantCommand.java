package com.omgservers.ctl.command.developer.sub.localtesting.sub;

import com.omgservers.ctl.operation.command.developer.localtesting.DeveloperLocalTestingDeleteTenantOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-tenant",
        description = "Delete a tenant created for local testing.")
public class DeveloperLocalTestingDeleteTenantCommand implements Runnable {

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
    DeveloperLocalTestingDeleteTenantOperation developerLocalTestingDeleteTenantOperation;

    @Override
    public void run() {
        developerLocalTestingDeleteTenantOperation.execute(tenant, project, stage);
    }
}
