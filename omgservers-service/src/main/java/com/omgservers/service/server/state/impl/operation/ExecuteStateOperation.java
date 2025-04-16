package com.omgservers.service.server.state.impl.operation;

public interface ExecuteStateOperation {

    void setServiceToken(String serviceToken);

    String getServiceToken();

    void setNodeId(Long nodeId);

    Long getNodeId();
}
