package com.omgservers.service.operation.getConfig;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetConfigOperationImpl implements GetConfigOperation {

    final ServiceConfig serviceConfig;

    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }
}
