package com.omgservers.service.operation.getServiceConfig;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetServiceConfigOperationImpl implements GetServiceConfigOperation {

    final ServiceConfig serviceConfig;

    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }
}
