package com.omgservers.dispatcher.operation.getDispatcherConfig;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetDispatcherConfigOperationImpl implements GetDispatcherConfigOperation {

    final DispatcherConfig dispatcherConfig;

    public DispatcherConfig getDispatcherConfig() {
        return dispatcherConfig;
    }
}
