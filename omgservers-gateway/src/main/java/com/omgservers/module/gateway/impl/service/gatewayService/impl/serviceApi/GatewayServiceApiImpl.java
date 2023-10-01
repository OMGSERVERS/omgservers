package com.omgservers.module.gateway.impl.service.gatewayService.impl.serviceApi;

import com.omgservers.dto.gateway.AssignPlayerRequest;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.gateway.impl.service.gatewayService.GatewayService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GatewayServiceApiImpl implements GatewayServiceApi {

    final GatewayService gatewayService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, gatewayService::respondMessage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> assignPlayer(AssignPlayerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, gatewayService::assignPlayer);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> assignRuntime(AssignRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, gatewayService::assignRuntime);
    }
}
