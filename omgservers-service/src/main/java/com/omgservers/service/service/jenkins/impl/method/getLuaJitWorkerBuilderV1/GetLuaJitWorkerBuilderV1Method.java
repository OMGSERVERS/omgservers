package com.omgservers.service.service.jenkins.impl.method.getLuaJitWorkerBuilderV1;

import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Request;
import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Response;
import io.smallrye.mutiny.Uni;

public interface GetLuaJitWorkerBuilderV1Method {
    Uni<GetLuaJitWorkerBuilderV1Response> getLuaJitWorkerBuilderV1(GetLuaJitWorkerBuilderV1Request request);
}



