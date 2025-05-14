package com.omgservers.service.server.state.impl.method;

import com.omgservers.service.server.state.dto.GetX5CRequest;
import com.omgservers.service.server.state.dto.GetX5CResponse;

public interface GetX5CMethod {
    GetX5CResponse execute(GetX5CRequest request);
}
