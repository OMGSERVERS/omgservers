package com.omgservers.service.server.state.impl.method;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationScoped
class StateServiceState {

    final AtomicReference<String> serviceToken;
    final AtomicReference<Long> nodeId;

    StateServiceState() {
        serviceToken = new AtomicReference<>();
        nodeId = new AtomicReference<>();
    }

    String getServiceToken() {
        return serviceToken.get();
    }

    void setServiceToken(final String serviceToken) {
        this.serviceToken.set(serviceToken);
    }

    Long getNodeId() {
        return nodeId.get();
    }

    void setNodeId(final Long nodeId) {
        this.nodeId.set(nodeId);
    }
}
