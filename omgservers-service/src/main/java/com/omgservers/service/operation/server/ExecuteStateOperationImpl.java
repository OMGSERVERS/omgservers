package com.omgservers.service.operation.server;

import com.omgservers.schema.model.index.IndexConfigDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@ApplicationScoped
class ExecuteStateOperationImpl implements ExecuteStateOperation {

    final AtomicReference<IndexConfigDto> indexConfig;
    final AtomicReference<String> serviceToken;
    final AtomicReference<Long> nodeId;
    final AtomicReference<String> x5c;

    ExecuteStateOperationImpl() {
        indexConfig = new AtomicReference<>();
        serviceToken = new AtomicReference<>();
        nodeId = new AtomicReference<>();
        x5c = new AtomicReference<>();
    }

    @Override
    public void setIndexConfig(final IndexConfigDto indexConfig) {
        this.indexConfig.set(indexConfig);
        log.info("State updated, index config set");
    }

    @Override
    public IndexConfigDto getIndexConfig() {
        return indexConfig.get();
    }

    @Override
    public void setServiceToken(final String serviceToken) {
        this.serviceToken.set(serviceToken);
        log.info("State updated, service token set");
    }

    @Override
    public String getServiceToken() {
        return serviceToken.get();
    }

    @Override
    public void setNodeId(final Long nodeId) {
        this.nodeId.set(nodeId);
        log.info("State updated, node id set");
    }

    @Override
    public Long getNodeId() {
        return nodeId.get();
    }

    @Override
    public void setX5C(final String x5c) {
        this.x5c.set(x5c);
        log.info("State updated, x5c set");
    }

    @Override
    public String getX5C() {
        return x5c.get();
    }
}