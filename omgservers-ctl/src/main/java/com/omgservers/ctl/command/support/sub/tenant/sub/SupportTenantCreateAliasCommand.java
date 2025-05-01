package com.omgservers.ctl.command.support.sub.tenant.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.operation.command.support.tenant.SupportTenantCreateAliasOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-alias",
        description = "Assign an alias to a tenant.")
public class SupportTenantCreateAliasCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id of the tenant to assign the alias to.")
    Long tenantId;

    @CommandLine.Parameters(description = "Alias to assign to the tenant.")
    String alias;

    @Inject
    SupportTenantCreateAliasOperation supportTenantCreateAliasOperation;

    @Override
    public void run() {
        supportTenantCreateAliasOperation.execute(tenantId, alias, service, user);
    }
}
