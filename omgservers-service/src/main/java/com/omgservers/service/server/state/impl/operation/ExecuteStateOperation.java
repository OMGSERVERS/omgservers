package com.omgservers.service.server.state.impl.operation;

import com.omgservers.schema.model.index.IndexConfigDto;

public interface ExecuteStateOperation {

    void setIndexConfig(IndexConfigDto indexConfig);

    IndexConfigDto getIndexConfig();

    void setServerToken(String sererToken);

    String getServerToken();

    void setNodeId(Long nodeId);

    Long getNodeId();
}
