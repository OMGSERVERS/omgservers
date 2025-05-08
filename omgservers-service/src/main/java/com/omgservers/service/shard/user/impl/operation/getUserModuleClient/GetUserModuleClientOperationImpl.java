package com.omgservers.service.shard.user.impl.operation.getUserModuleClient;

import com.omgservers.service.operation.server.GetRestClientOperationImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetUserModuleClientOperationImpl extends GetRestClientOperationImpl<UserModuleClient>
        implements GetUserModuleClientOperation {

    public GetUserModuleClientOperationImpl() {
        super(UserModuleClient.class);
    }
}
