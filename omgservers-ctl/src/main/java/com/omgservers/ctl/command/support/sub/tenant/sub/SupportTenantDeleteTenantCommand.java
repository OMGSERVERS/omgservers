package com.omgservers.ctl.command.support.sub.tenant.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.support.tenant.SupportTenantDeleteTenantOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-tenant",
        description = "Delete a tenant by id or alias.")
public class SupportTenantDeleteTenantCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant to delete.")
    String tenant;

    @Inject
    SupportTenantDeleteTenantOperation supportTenantDeleteTenantOperation;

    @Override
    public void run() {
        supportTenantDeleteTenantOperation.execute(tenant, installation, user);
    }
}
