package com.omgservers.service.operation.server;

import com.omgservers.schema.model.index.IndexConfigDto;

public interface ExecuteStateOperation {

    void setIndexConfig(IndexConfigDto indexConfig);

    IndexConfigDto getIndexConfig();

    void setServiceToken(String serviceToken);

    String getServiceToken();

    void setNodeId(Long nodeId);

    Long getNodeId();

    void setX5C(String x5c);

    String getX5C();
}
