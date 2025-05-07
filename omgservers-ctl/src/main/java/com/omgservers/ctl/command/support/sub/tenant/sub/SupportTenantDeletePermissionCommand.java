package com.omgservers.ctl.command.support.sub.tenant.sub;

import com.omgservers.ctl.command.UserCommand;
import com.omgservers.ctl.command.support.sub.tenant.sub.util.TenantPermissionCandidates;
import com.omgservers.ctl.command.support.sub.tenant.sub.util.TenantPermissionConverter;
import com.omgservers.ctl.dto.permission.TenantPermissionEnum;
import com.omgservers.ctl.operation.command.support.tenant.SupportTenantDeletePermissionOperation;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(
        name = "delete-permission",
        description = "Revoke a user's permission for a tenant.")
public class SupportTenantDeletePermissionCommand extends UserCommand {

    @CommandLine.Parameters(description = "Id or alias of the tenant from which the permission will be revoked.")
    String tenant;

    @CommandLine.Parameters(description = "Id or alias of the developer user.")
    String developer;

    @CommandLine.Parameters(description = "Tenant permission to revoke. Possible values: ${COMPLETION-CANDIDATES}.",
            converter = TenantPermissionConverter.class,
            completionCandidates = TenantPermissionCandidates.class)
    TenantPermissionEnum permission;

    @Inject
    SupportTenantDeletePermissionOperation supportTenantDeletePermissionOperation;

    @Override
    public void run() {
        supportTenantDeletePermissionOperation.execute(tenant, developer, permission, installation, user);
    }
}
