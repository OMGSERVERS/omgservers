package com.omgservers.service.entrypoint.runtime.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.entrypoint.runtime.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RuntimeService runtimeService;

    @Override
    public Uni<CreateTokenRuntimeResponse> createToken(final CreateTokenRuntimeRequest request) {
        return runtimeService.createToken(request);
    }

    @Override
    public Uni<GetConfigRuntimeResponse> getConfig(final GetConfigRuntimeRequest request) {
        return runtimeService.getConfig(request);
    }

    @Override
    public Uni<InterchangeRuntimeResponse> interchange(final InterchangeRuntimeRequest request) {
        return runtimeService.interchange(request);
    }
}
