package com.omgservers.service.integration.jenkins.impl.service.jenkinsService.impl.method.getLuaJitWorkerBuilderV1;

import com.omgservers.model.dto.jenkins.GetLuaJitWorkerBuilderV1Request;
import com.omgservers.model.dto.jenkins.GetLuaJitWorkerBuilderV1Response;
import io.smallrye.mutiny.Uni;

public interface GetLuaJitWorkerBuilderV1Method {
    Uni<GetLuaJitWorkerBuilderV1Response> getLuaJitWorkerBuilderV1(GetLuaJitWorkerBuilderV1Request request);
}



