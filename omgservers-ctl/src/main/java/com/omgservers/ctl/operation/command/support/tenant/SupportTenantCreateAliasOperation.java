package com.omgservers.ctl.operation.command.support.tenant;

public interface SupportTenantCreateAliasOperation {

    void execute(Long tenantId, String alias, String installation);
}
