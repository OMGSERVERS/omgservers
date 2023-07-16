package com.omgservers.application.operation.getConfigOperation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetConfigOperationImpl implements GetConfigOperation {

    final ServiceApplicationConfig serviceApplicationConfig;

    public ServiceApplicationConfig getConfig() {
        return serviceApplicationConfig;
    }
}
