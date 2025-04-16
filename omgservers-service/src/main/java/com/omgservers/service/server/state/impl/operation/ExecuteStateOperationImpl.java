package com.omgservers.service.server.state.impl.operation;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationScoped
class ExecuteStateOperationImpl implements ExecuteStateOperation {

    final AtomicReference<String> serviceToken;
    final AtomicReference<Long> nodeId;

    ExecuteStateOperationImpl() {
        serviceToken = new AtomicReference<>();
        nodeId = new AtomicReference<>();
    }

    @Override
    public void setServiceToken(final String serviceToken) {
        this.serviceToken.set(serviceToken);
    }

    @Override
    public String getServiceToken() {
        return serviceToken.get();
    }

    @Override
    public void setNodeId(final Long nodeId) {
        this.nodeId.set(nodeId);
    }

    @Override
    public Long getNodeId() {
        return nodeId.get();
    }
}