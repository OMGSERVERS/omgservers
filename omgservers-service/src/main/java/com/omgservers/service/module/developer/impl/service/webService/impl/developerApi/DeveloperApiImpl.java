package com.omgservers.service.module.developer.impl.service.webService.impl.developerApi;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.module.developer.impl.service.webService.WebService;
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
class DeveloperApiImpl implements DeveloperApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenDeveloperResponse> createToken(final CreateTokenDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createProject);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<CreateVersionDeveloperResponse> createVersion(final CreateVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createVersion);
    }
}
