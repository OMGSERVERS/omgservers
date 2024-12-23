package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeRuntimeResponse;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.createToken.CreateTokenMethod;
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

    @Override
    public Uni<CreateTokenRuntimeResponse> execute(@Valid final CreateTokenRuntimeRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<InterchangeRuntimeResponse> execute(@Valid final InterchangeRuntimeRequest request) {
        return interchangeMethod.execute(request);
    }
}
