package com.omgservers.service.shard.deployment.impl.operation.getDeploymentModuleClient;

import java.net.URI;

public interface GetDeploymentModuleClientOperation {
    DeploymentModuleClient getClient(URI uri);

    Boolean hasCacheFor(URI uri);

    Integer sizeOfCache();
}
