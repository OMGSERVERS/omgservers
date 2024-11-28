package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

import com.omgservers.schema.entrypoint.admin.CreateTokenAdminRequest;
import com.omgservers.schema.entrypoint.admin.CreateTokenAdminResponse;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminRequest;
import com.omgservers.schema.entrypoint.admin.PingDockerHostAdminResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.admin.impl.service.webService.WebService;
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
@RolesAllowed({UserRoleEnum.Names.ADMIN})
public class AdminApiImpl implements AdminApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenAdminResponse> execute(@NotNull final CreateTokenAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.ADMIN})
    public Uni<PingDockerHostAdminResponse> execute(final PingDockerHostAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
