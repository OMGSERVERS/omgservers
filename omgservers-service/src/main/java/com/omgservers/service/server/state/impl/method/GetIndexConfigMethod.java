package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetIndexConfigRequest;
import com.omgservers.service.server.state.dto.GetIndexConfigResponse;

public interface GetIndexConfigMethod {
    GetIndexConfigResponse execute(GetIndexConfigRequest request);
}
