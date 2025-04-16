package com.omgservers.service.server.state.impl.operation;

public interface ChangeStateOperation {

    void setServiceToken(String serviceToken);

    String getServiceToken();

    void setNodeId(Long nodeId);

    Long getNodeId();
}
