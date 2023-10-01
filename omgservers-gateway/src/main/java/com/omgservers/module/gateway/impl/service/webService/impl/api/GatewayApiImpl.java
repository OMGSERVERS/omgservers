package com.omgservers.module.gateway.impl.service.webService.impl.api;

import com.omgservers.dto.gateway.AssignClientRequest;
import com.omgservers.dto.gateway.AssignRuntimeRequest;
import com.omgservers.dto.gateway.RespondMessageRequest;
import com.omgservers.dto.gateway.RespondMessageResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.gateway.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GatewayApiImpl implements GatewayApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<RespondMessageResponse> respondMessage(RespondMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::respondMessage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> assignClient(AssignClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::assignClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> assignRuntime(AssignRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::assignRuntime);
    }
}
