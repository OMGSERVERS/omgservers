package com.omgservers.connector.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class GetServiceClientOperationImpl extends GetRestClientOperationImpl<ServiceClient>
        implements GetServiceClientOperation {

    public GetServiceClientOperationImpl() {
        super(ServiceClient.class);
    }
}
