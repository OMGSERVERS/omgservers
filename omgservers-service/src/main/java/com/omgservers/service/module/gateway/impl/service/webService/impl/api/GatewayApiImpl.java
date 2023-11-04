package com.omgservers.service.module.gateway.impl.service.webService.impl.api;

import com.omgservers.model.dto.gateway.AssignClientRequest;
import com.omgservers.model.dto.gateway.AssignClientResponse;
import com.omgservers.model.dto.gateway.AssignRuntimeRequest;
import com.omgservers.model.dto.gateway.AssignRuntimeResponse;
import com.omgservers.model.dto.gateway.RespondMessageRequest;
import com.omgservers.model.dto.gateway.RespondMessageResponse;
import com.omgservers.model.dto.gateway.RevokeRuntimeRequest;
import com.omgservers.model.dto.gateway.RevokeRuntimeResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.gateway.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
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
    public Uni<RespondMessageResponse> respondMessage(final RespondMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::respondMessage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<AssignClientResponse> assignClient(final AssignClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::assignClient);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<AssignRuntimeResponse> assignRuntime(final AssignRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::assignRuntime);
    }

    @Override
    public Uni<RevokeRuntimeResponse> revokeRuntime(final RevokeRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::revokeRuntime);
    }
}
