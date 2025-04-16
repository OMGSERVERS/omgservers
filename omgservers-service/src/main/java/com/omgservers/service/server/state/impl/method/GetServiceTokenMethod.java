package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetServiceTokenRequest;
import com.omgservers.service.server.state.dto.GetServiceTokenResponse;

public interface GetServiceTokenMethod {
    GetServiceTokenResponse execute(GetServiceTokenRequest request);
}
