package com.omgservers.base.impl.operation.getConfigOperation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetConfigOperationImpl implements GetConfigOperation {

    final ServiceApplicationConfig serviceApplicationConfig;

    public ServiceApplicationConfig getConfig() {
        return serviceApplicationConfig;
    }
}
