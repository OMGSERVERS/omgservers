package com.omgservers.service.shard.deployment.impl.operation.getDeploymentModuleClient;

import java.net.URI;

public interface GetDeploymentModuleClientOperation {
    DeploymentModuleClient execute(URI uri);
}
