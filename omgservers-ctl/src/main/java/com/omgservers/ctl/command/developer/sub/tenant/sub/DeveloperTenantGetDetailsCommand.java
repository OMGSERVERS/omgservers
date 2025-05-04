package com.omgservers.ctl.command.developer.sub.tenant.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.developer.tenant.DeveloperTenantGetDetailsOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "get-details",
        description = "Get details of a tenant.")
public class DeveloperTenantGetDetailsCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant to get details.")
    String tenant;

    @Inject
    DeveloperTenantGetDetailsOperation developerTenantGetDetailsOperation;

    @Override
    public void run() {
        developerTenantGetDetailsOperation.execute(tenant, installation, user, prettyPrint);
    }
}
