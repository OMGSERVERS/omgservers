package com.omgservers.module.gateway.impl.service.gatewayWebService.impl.serviceApi;

import com.omgservers.module.gateway.impl.service.gatewayWebService.GatewayWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.gateway.AssignPlayerRoutedRequest;
import com.omgservers.dto.gateway.RespondMessageRoutedRequest;
import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GatewayServiceApiImpl implements GatewayServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final GatewayWebService gatewayWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> respondMessage(RespondMessageRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, gatewayWebService::respondMessage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> assignPlayer(AssignPlayerRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, gatewayWebService::assignPlayer);
    }
}
