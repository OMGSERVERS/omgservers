package com.omgservers.service.shard.deployment.impl.operation.getDeploymentModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetDeploymentModuleClientOperationImpl extends GetRestClientOperationImpl<DeploymentModuleClient>
        implements GetDeploymentModuleClientOperation {

    public GetDeploymentModuleClientOperationImpl() {
        super(DeploymentModuleClient.class);
    }
}
