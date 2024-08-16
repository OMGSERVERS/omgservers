package com.omgservers.service.service.jenkins.impl.method.runLuaJitRuntimeBuilderV1;

import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Response;
import io.smallrye.mutiny.Uni;

public interface RunLuaJitRuntimeBuilderV1Method {
    Uni<RunLuaJitRuntimeBuilderV1Response> runLuaJitRuntimeBuilderV1(RunLuaJitRuntimeBuilderV1Request request);
}
