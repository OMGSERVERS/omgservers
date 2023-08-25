package com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService.impl;

import com.omgservers.application.module.gatewayModule.impl.service.gatewayInternalService.GatewayInternalService;
import com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService.GatewayWebService;
import com.omgservers.dto.gatewayModule.AssignPlayerInternalRequest;
import com.omgservers.dto.gatewayModule.RespondMessageInternalRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GatewayWebServiceImpl implements GatewayWebService {

    final GatewayInternalService gatewayInternalService;

    @Override
    public Uni<Void> respondMessage(RespondMessageInternalRequest request) {
        return gatewayInternalService.respondMessage(request);
    }

    @Override
    public Uni<Void> assignPlayer(AssignPlayerInternalRequest request) {
        return gatewayInternalService.assignPlayer(request);
    }
}