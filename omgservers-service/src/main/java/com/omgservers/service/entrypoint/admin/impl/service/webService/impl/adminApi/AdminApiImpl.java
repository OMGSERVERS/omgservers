package com.omgservers.service.entrypoint.admin.impl.service.webService.impl.adminApi;

import com.omgservers.model.dto.admin.CreateTokenAdminRequest;
import com.omgservers.model.dto.admin.CreateTokenAdminResponse;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.admin.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
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
    public Uni<CreateTokenAdminResponse> createToken(final CreateTokenAdminRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }
}
