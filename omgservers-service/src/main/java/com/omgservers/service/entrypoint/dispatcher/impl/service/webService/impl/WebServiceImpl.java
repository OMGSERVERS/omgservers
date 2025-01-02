package com.omgservers.service.entrypoint.dispatcher.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.DispatcherService;
import com.omgservers.service.entrypoint.dispatcher.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final DispatcherService dispatcherService;

    @Override
    public Uni<CreateTokenDispatcherResponse> execute(final CreateTokenDispatcherRequest request) {
        return dispatcherService.execute(request);
    }

    @Override
    public Uni<CalculateShardDispatcherResponse> execute(final CalculateShardDispatcherRequest request) {
        return dispatcherService.execute(request);
    }
}
