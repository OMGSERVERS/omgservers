package com.omgservers.application.module.developerModule.impl.service.developerWebService.impl.developerApi;

import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import com.omgservers.dto.developerModule.CreateTokenDeveloperResponse;
import com.omgservers.dto.developerModule.CreateVersionDeveloperResponse;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperResponse;
import com.omgservers.application.module.developerModule.impl.service.developerWebService.DeveloperWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.model.user.UserRoleEnum;
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

    final HandleApiRequestOperation handleApiRequestOperation;
    final DeveloperWebService developerWebService;

    @Override
    @PermitAll
    public Uni<CreateTokenDeveloperResponse> createToken(final CreateTokenDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::createToken);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::createProject);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<CreateVersionDeveloperResponse> createVersion(final CreateVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::createVersion);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::getVersionStatus);
    }
}
