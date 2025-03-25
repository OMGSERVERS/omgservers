package com.omgservers.service.shard.deployment;

import com.omgservers.service.shard.deployment.impl.service.deploymentService.DeploymentService;

public interface DeploymentShard {

    DeploymentService getService();
}
