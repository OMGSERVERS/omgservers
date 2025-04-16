package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetNodeIdRequest;
import com.omgservers.service.server.state.dto.GetNodeIdResponse;

public interface GetNodeIdMethod {
    GetNodeIdResponse execute(GetNodeIdRequest request);
}
