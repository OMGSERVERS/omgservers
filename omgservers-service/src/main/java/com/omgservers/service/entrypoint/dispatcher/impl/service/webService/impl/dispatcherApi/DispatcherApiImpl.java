package com.omgservers.service.entrypoint.dispatcher.impl.service.webService.impl.dispatcherApi;

import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CalculateShardDispatcherResponse;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherRequest;
import com.omgservers.schema.entrypoint.dispatcher.CreateTokenDispatcherResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.dispatcher.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
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
@RolesAllowed({UserRoleEnum.Names.DISPATCHER})
public class DispatcherApiImpl implements DispatcherApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenDispatcherResponse> execute(@NotNull final CreateTokenDispatcherRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CalculateShardDispatcherResponse> execute(final CalculateShardDispatcherRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
