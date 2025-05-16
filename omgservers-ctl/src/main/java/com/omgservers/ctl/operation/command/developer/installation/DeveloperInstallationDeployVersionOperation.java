package com.omgservers.ctl.operation.command.developer.installation;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public interface DeveloperInstallationDeployVersionOperation {

    void execute(String developer,
                 String password,
                 String tenant,
                 String project,
                 String stage,
                 String image,
                 TenantVersionConfigDto versionConfig,
                 DeploymentConfigDto deploymentConfig,
                 String installation);
}
