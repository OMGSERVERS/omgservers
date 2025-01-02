package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.DispatcherService;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method.CalculateShardMethod;
import com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method.CreateTokenMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DispatcherServiceImpl implements DispatcherService {

    final CalculateShardMethod calculateShardMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenDispatcherResponse> execute(@Valid final CreateTokenDispatcherRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<CalculateShardDispatcherResponse> execute(@Valid final CalculateShardDispatcherRequest request) {
        return calculateShardMethod.execute(request);
    }
}
