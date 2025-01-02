package com.omgservers.dispatcher.service.service.impl;

import com.omgservers.dispatcher.service.service.ServiceService;
import com.omgservers.dispatcher.service.service.dto.CalculateShardRequest;
import com.omgservers.dispatcher.service.service.dto.CalculateShardResponse;
import com.omgservers.dispatcher.service.service.dto.CreateTokenRequest;
import com.omgservers.dispatcher.service.service.dto.CreateTokenResponse;
import com.omgservers.dispatcher.service.service.impl.method.CalculateShardMethod;
import com.omgservers.dispatcher.service.service.impl.method.CreateTokenMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServiceServiceImpl implements ServiceService {

    final CalculateShardMethod calculateShardMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenResponse> execute(@Valid final CreateTokenRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<CalculateShardResponse> execute(@Valid final CalculateShardRequest request) {
        return calculateShardMethod.execute(request);
    }
}
