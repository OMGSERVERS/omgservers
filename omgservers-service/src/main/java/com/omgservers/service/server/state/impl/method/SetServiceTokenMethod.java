package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.SetServiceTokenRequest;
import com.omgservers.service.server.state.dto.SetServiceTokenResponse;

public interface SetServiceTokenMethod {
    SetServiceTokenResponse execute(SetServiceTokenRequest request);
}
