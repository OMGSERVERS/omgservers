package com.omgservers.service.integration.jenkins.impl.service.jenkinsService;

import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Request;
import com.omgservers.schema.module.jenkins.GetLuaJitWorkerBuilderV1Response;
import com.omgservers.schema.module.jenkins.RunLuaJitWorkerBuilderV1Request;
import com.omgservers.schema.module.jenkins.RunLuaJitWorkerBuilderV1Response;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface JenkinsService {
    Uni<RunLuaJitWorkerBuilderV1Response> runLuaJitWorkerBuilderV1(@Valid RunLuaJitWorkerBuilderV1Request request);

    Uni<GetLuaJitWorkerBuilderV1Response> getLuaJitWorkerBuilderV1(@Valid GetLuaJitWorkerBuilderV1Request request);
}
