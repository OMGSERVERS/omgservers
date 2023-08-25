package com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService.impl.serviceApi;

import com.omgservers.application.module.gatewayModule.impl.service.gatewayWebService.GatewayWebService;
import com.omgservers.base.impl.operation.handleApiRequestOperation.HandleApiRequestOperation;
import com.omgservers.dto.gatewayModule.AssignPlayerInternalRequest;
import com.omgservers.dto.gatewayModule.RespondMessageInternalRequest;
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
    public Uni<Void> respondMessage(RespondMessageInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, gatewayWebService::respondMessage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> assignPlayer(AssignPlayerInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, gatewayWebService::assignPlayer);
    }
}
