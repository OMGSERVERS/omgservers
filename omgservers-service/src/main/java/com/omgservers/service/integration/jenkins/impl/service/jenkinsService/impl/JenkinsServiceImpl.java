package com.omgservers.service.integration.jenkins.impl.service.jenkinsService.impl;

import com.omgservers.model.dto.jenkins.GetLuaJitWorkerBuilderV1Request;
import com.omgservers.model.dto.jenkins.GetLuaJitWorkerBuilderV1Response;
import com.omgservers.model.dto.jenkins.RunLuaJitWorkerBuilderV1Request;
import com.omgservers.model.dto.jenkins.RunLuaJitWorkerBuilderV1Response;
import com.omgservers.service.integration.jenkins.impl.service.jenkinsService.JenkinsService;
import com.omgservers.service.integration.jenkins.impl.service.jenkinsService.impl.method.getLuaJitWorkerBuilderV1.GetLuaJitWorkerBuilderV1Method;
import com.omgservers.service.integration.jenkins.impl.service.jenkinsService.impl.method.runLuaJitWorkerBuilderV1.RunLuaJitWorkerBuilderV1Method;
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

    final RunLuaJitWorkerBuilderV1Method runLuaJitWorkerBuilderV1Method;
    final GetLuaJitWorkerBuilderV1Method getLuaJitWorkerBuilderV1Method;

    @Override
    public Uni<RunLuaJitWorkerBuilderV1Response> runLuaJitWorkerBuilderV1(
            @Valid final RunLuaJitWorkerBuilderV1Request request) {
        return runLuaJitWorkerBuilderV1Method.runLuaJitWorkerBuilderV1(request);
    }

    @Override
    public Uni<GetLuaJitWorkerBuilderV1Response> getLuaJitWorkerBuilderV1(
            @Valid final GetLuaJitWorkerBuilderV1Request request) {
        return getLuaJitWorkerBuilderV1Method.getLuaJitWorkerBuilderV1(request);
    }
}
