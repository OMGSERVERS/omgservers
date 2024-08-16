package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.GetConfigRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.getConfig.GetConfigMethod;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.interchangeMethod.InterchangeMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeServiceImpl implements RuntimeService {

    final InterchangeMethod interchangeMethod;
    final CreateTokenMethod createTokenMethod;
    final GetConfigMethod getConfigMethod;

    @Override
    public Uni<CreateTokenRuntimeResponse> createToken(@Valid final CreateTokenRuntimeRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<GetConfigRuntimeResponse> getConfig(@Valid final GetConfigRuntimeRequest request) {
        return getConfigMethod.getConfig(request);
    }

    @Override
    public Uni<InterchangeRuntimeResponse> interchange(@Valid final InterchangeRuntimeRequest request) {
        return interchangeMethod.interchange(request);
    }
}
