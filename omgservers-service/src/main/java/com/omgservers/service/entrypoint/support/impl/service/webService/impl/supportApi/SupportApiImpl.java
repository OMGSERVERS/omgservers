package com.omgservers.service.entrypoint.support.impl.service.webService.impl.supportApi;

import com.omgservers.model.dto.support.CreateDeveloperSupportRequest;
import com.omgservers.model.dto.support.CreateDeveloperSupportResponse;
import com.omgservers.model.dto.support.CreateTenantSupportRequest;
import com.omgservers.model.dto.support.CreateTenantSupportResponse;
import com.omgservers.model.dto.support.CreateTokenSupportRequest;
import com.omgservers.model.dto.support.CreateTokenSupportResponse;
import com.omgservers.model.dto.support.DeleteTenantSupportRequest;
import com.omgservers.model.dto.support.DeleteTenantSupportResponse;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.support.impl.service.webService.WebService;
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
@RolesAllowed({UserRoleEnum.Names.SUPPORT})
public class SupportApiImpl implements SupportApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenSupportResponse> createToken(final CreateTokenSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    public Uni<CreateTenantSupportResponse> createTenant(final CreateTenantSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenant);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(DeleteTenantSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenant);
    }

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(final CreateDeveloperSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createDeveloper);
    }
}
