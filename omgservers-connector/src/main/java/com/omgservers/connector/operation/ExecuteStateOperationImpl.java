package com.omgservers.connector.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationScoped
class ExecuteStateOperationImpl implements ExecuteStateOperation {

    final AtomicReference<String> connectorToken;

    ExecuteStateOperationImpl() {
        connectorToken = new AtomicReference<>();
    }

    @Override
    public void setConnectorToken(String connectorToken) {
        this.connectorToken.set(connectorToken);
        log.info("State updated, connector token set");
    }

    @Override
    public String getConnectorToken() {
        return connectorToken.get();
    }
}