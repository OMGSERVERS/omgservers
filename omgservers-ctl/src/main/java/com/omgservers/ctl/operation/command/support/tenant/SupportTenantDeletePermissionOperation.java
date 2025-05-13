package com.omgservers.ctl.operation.command.support.tenant;

import com.omgservers.ctl.dto.permission.TenantPermissionEnum;

public interface SupportTenantDeletePermissionOperation {

    void execute(String tenant,
                 String developer,
                 TenantPermissionEnum permission,
                 String installation);
}
