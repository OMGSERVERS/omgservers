package com.omgservers.ctl.command.support.sub.tenant.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.command.support.sub.tenant.sub.util.TenantPermissionCandidates;
import com.omgservers.ctl.command.support.sub.tenant.sub.util.TenantPermissionConverter;
import com.omgservers.ctl.dto.permission.TenantPermissionEnum;
import com.omgservers.ctl.operation.command.support.tenant.SupportTenantCreatePermissionOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "create-permission",
        description = "Grant a permission to a user for a tenant.")
public class SupportTenantCreatePermissionCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant to which the permission will be granted.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Tenant permission to grant. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = TenantPermissionConverter.class,
            completionCandidates = TenantPermissionCandidates.class)
    TenantPermissionEnum permission;

    @Inject
    SupportTenantCreatePermissionOperation supportTenantCreatePermissionOperation;

    @Override
    public void run() {
        supportTenantCreatePermissionOperation.execute(tenant, developer, permission, installation, user);
    }
}
