package com.omgservers.service.service.jenkins.impl.method.getLuaJitRuntimeBuilderV1;

import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Response;
import io.smallrye.mutiny.Uni;

public interface GetLuaJitRuntimeBuilderV1Method {
    Uni<GetLuaJitRuntimeBuilderV1Response> getLuaJitRuntimeBuilderV1(GetLuaJitRuntimeBuilderV1Request request);
}



