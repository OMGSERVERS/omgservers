package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetIndexConfigRequest;
import com.omgservers.service.server.state.dto.SetIndexConfigResponse;

public interface SetIndexConfigMethod {
    SetIndexConfigResponse execute(SetIndexConfigRequest request);
}
