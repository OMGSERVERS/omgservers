package com.omgservers.ctl.operation.command.developer.version;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public interface DeveloperVersionCreateVersionOperation {

    void execute(String tenant,
                 String project,
                 TenantVersionConfigDto config,
                 String service,
                 String user);
}
