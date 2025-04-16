package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetNodeIdRequest;
import com.omgservers.service.server.state.dto.SetNodeIdResponse;

public interface SetNodeIdMethod {
    SetNodeIdResponse execute(SetNodeIdRequest request);
}
