package com.omgservers.connector.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetUserAgentOperationImpl implements GetUserAgentOperation {

    final GetVersionOperation getVersionOperation;

    @Override
    public String execute() {
        final var userAgent = "OMGCONNECTORv" + getVersionOperation.execute();
        return userAgent;
    }
}
