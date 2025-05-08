package com.omgservers.service.shard.docker.impl.operation;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetDockerModuleClientOperationImpl extends GetRestClientOperationImpl<DockerModuleClient>
        implements GetDockerModuleClientOperation {

    public GetDockerModuleClientOperationImpl() {
        super(DockerModuleClient.class);
    }
}
