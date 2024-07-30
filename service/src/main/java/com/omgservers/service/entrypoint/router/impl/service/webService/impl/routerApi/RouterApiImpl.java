package com.omgservers.service.entrypoint.router.impl.service.webService.impl.routerApi;

import com.omgservers.schema.entrypoint.router.CreateTokenRouterRequest;
import com.omgservers.schema.entrypoint.router.CreateTokenRouterResponse;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.router.impl.service.webService.WebService;
import com.omgservers.service.server.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.ROUTER})
class RouterApiImpl implements RouterApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenRouterResponse> createToken(CreateTokenRouterRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    public Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(
            @NotNull final GetRuntimeServerUriRouterRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRuntimeServerUri);
    }
}