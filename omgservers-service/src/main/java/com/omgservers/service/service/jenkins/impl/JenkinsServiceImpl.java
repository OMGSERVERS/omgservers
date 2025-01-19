package com.omgservers.service.service.jenkins.impl;

import com.omgservers.service.service.jenkins.JenkinsService;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.GetLuaJitRuntimeBuilderV1Response;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Request;
import com.omgservers.service.service.jenkins.dto.RunLuaJitRuntimeBuilderV1Response;
import com.omgservers.service.service.jenkins.impl.method.getLuaJitRuntimeBuilderV1.GetLuaJitRuntimeBuilderV1Method;
import com.omgservers.service.service.jenkins.impl.method.runLuaJitRuntimeBuilderV1.RunLuaJitRuntimeBuilderV1Method;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class JenkinsServiceImpl implements JenkinsService {

    final RunLuaJitRuntimeBuilderV1Method runLuaJitRuntimeBuilderV1Method;
    final GetLuaJitRuntimeBuilderV1Method getLuaJitRuntimeBuilderV1Method;

    @Override
    public Uni<RunLuaJitRuntimeBuilderV1Response> runLuaJitRuntimeBuilderV1(
            @Valid final RunLuaJitRuntimeBuilderV1Request request) {
        return runLuaJitRuntimeBuilderV1Method.runLuaJitRuntimeBuilderV1(request);
    }

    @Override
    public Uni<GetLuaJitRuntimeBuilderV1Response> getLuaJitRuntimeBuilderV1(
            @Valid final GetLuaJitRuntimeBuilderV1Request request) {
        return getLuaJitRuntimeBuilderV1Method.getLuaJitRuntimeBuilderV1(request);
    }
}
