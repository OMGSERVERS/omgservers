package com.omgservers.ctl.operation.command.developer.installation;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public interface DeveloperInstallationDeployVersionOperation {

    void execute(String developer,
                 String password,
                 String tenant,
                 String project,
                 String stage,
                 TenantVersionConfigDto config,
                 String image,
                 String installation);
}
