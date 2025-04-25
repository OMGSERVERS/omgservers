package com.omgservers.ctl.operation.command.support.tenant;

import com.omgservers.ctl.dto.permission.TenantPermissionEnum;

public interface SupportTenantCreatePermissionOperation {

    void execute(String tenant,
                 Long userId,
                 TenantPermissionEnum permission,
                 String service,
                 String user);
}
