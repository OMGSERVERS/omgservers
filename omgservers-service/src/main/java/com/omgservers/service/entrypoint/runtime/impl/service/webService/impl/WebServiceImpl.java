package com.omgservers.service.entrypoint.runtime.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.CreateTokenRuntimeResponse;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeRequest;
import com.omgservers.schema.entrypoint.runtime.InterchangeMessagesRuntimeResponse;
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
    public Uni<CreateTokenRuntimeResponse> execute(final CreateTokenRuntimeRequest request) {
        return runtimeService.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesRuntimeResponse> execute(final InterchangeMessagesRuntimeRequest request) {
        return runtimeService.execute(request);
    }
}
