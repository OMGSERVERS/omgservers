package com.omgservers.service.integration.jenkins.impl.service.jenkinsService.impl.method.runLuaJitWorkerBuilderV1;

import com.omgservers.model.dto.jenkins.RunLuaJitWorkerBuilderV1Request;
import com.omgservers.model.dto.jenkins.RunLuaJitWorkerBuilderV1Response;
import io.smallrye.mutiny.Uni;

public interface RunLuaJitWorkerBuilderV1Method {
    Uni<RunLuaJitWorkerBuilderV1Response> runLuaJitWorkerBuilderV1(RunLuaJitWorkerBuilderV1Request request);
}
