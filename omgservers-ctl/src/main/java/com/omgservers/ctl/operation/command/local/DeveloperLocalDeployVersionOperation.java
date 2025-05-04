package com.omgservers.ctl.operation.command.local;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public interface DeveloperLocalDeployVersionOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 TenantVersionConfigDto config,
                 String image);
}
