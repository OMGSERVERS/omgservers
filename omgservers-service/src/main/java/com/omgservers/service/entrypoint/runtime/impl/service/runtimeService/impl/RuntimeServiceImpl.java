package com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.entrypoint.runtime.impl.service.runtimeService.impl.method.interchangeMessagesMethod.InterchangeMessagesMethod;
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

    final InterchangeMessagesMethod interchangeMessagesMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenRuntimeResponse> execute(@Valid final CreateTokenRuntimeRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesRuntimeResponse> execute(@Valid final InterchangeMessagesRuntimeRequest request) {
        return interchangeMessagesMethod.execute(request);
    }
}
