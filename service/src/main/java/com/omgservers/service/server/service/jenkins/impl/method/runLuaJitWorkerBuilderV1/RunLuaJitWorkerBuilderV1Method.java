package com.omgservers.service.server.service.jenkins.impl.method.runLuaJitWorkerBuilderV1;

import com.omgservers.schema.module.jenkins.RunLuaJitWorkerBuilderV1Request;
import com.omgservers.schema.module.jenkins.RunLuaJitWorkerBuilderV1Response;
import io.smallrye.mutiny.Uni;

public interface RunLuaJitWorkerBuilderV1Method {
    Uni<RunLuaJitWorkerBuilderV1Response> runLuaJitWorkerBuilderV1(RunLuaJitWorkerBuilderV1Request request);
}
