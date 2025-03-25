package com.omgservers.service.shard.deployment.impl;

import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.deployment.impl.service.deploymentService.DeploymentService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeploymentShardImpl implements DeploymentShard {

    final DeploymentService deploymentService;

    @Override
    public DeploymentService getService() {
        return deploymentService;
    }

}
