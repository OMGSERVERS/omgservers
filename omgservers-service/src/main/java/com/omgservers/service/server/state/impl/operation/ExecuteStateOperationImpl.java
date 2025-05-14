package com.omgservers.service.server.state.impl.operation;

import com.omgservers.schema.model.index.IndexConfigDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationScoped
class ExecuteStateOperationImpl implements ExecuteStateOperation {

    final AtomicReference<IndexConfigDto> indexConfig;
    final AtomicReference<String> serverToken;
    final AtomicReference<Long> nodeId;
    final AtomicReference<String> x5c;

    ExecuteStateOperationImpl() {
        indexConfig = new AtomicReference<>();
        serverToken = new AtomicReference<>();
        nodeId = new AtomicReference<>();
        x5c = new AtomicReference<>();
    }

    @Override
    public void setIndexConfig(final IndexConfigDto indexConfig) {
        this.indexConfig.set(indexConfig);
    }

    @Override
    public IndexConfigDto getIndexConfig() {
        return indexConfig.get();
    }

    @Override
    public void setServerToken(final String sererToken) {
        this.serverToken.set(sererToken);
    }

    @Override
    public String getServerToken() {
        return serverToken.get();
    }

    @Override
    public void setNodeId(final Long nodeId) {
        this.nodeId.set(nodeId);
    }

    @Override
    public Long getNodeId() {
        return nodeId.get();
    }

    @Override
    public void setX5C(final String x5c) {
        this.x5c.set(x5c);
    }

    @Override
    public String getX5C() {
        return x5c.get();
    }
}