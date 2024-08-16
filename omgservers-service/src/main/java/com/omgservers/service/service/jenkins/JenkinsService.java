package com.omgservers.service.service.jenkins;

import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Response;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Response;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface JenkinsService {
    Uni<RunLuaJitRuntimeBuilderV1Response> runLuaJitRuntimeBuilderV1(@Valid RunLuaJitRuntimeBuilderV1Request request);

    Uni<GetLuaJitRuntimeBuilderV1Response> getLuaJitRuntimeBuilderV1(@Valid GetLuaJitRuntimeBuilderV1Request request);
}
