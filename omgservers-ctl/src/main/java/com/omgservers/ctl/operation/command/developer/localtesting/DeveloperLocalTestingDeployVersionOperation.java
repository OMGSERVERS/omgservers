package com.omgservers.ctl.operation.command.developer.localtesting;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;

public interface DeveloperLocalTestingDeployVersionOperation {

    void execute(String tenant,
                 String project,
                 String stage,
                 String image,
                 TenantVersionConfigDto versionConfig,
                 DeploymentConfigDto deploymentConfig);
}
