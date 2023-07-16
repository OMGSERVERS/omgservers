package com.omgservers.application.module.developerModule.impl.service.developerWebService.impl.developerApi;

import com.omgservers.application.module.developerModule.impl.service.developerWebService.DeveloperWebService;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateTokenHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
import com.omgservers.application.operation.handleApiRequestOperation.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeveloperApiImpl implements DeveloperApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final DeveloperWebService developerWebService;

    @Override
    @PermitAll
    public Uni<CreateTokenHelpResponse> createToken(final CreateTokenHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::createToken);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<CreateProjectHelpResponse> createProject(CreateProjectHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::createProject);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<CreateVersionHelpResponse> createVersion(final CreateVersionHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::createVersion);
    }

    @Override
    @RolesAllowed({UserRoleEnum.Names.DEVELOPER})
    public Uni<GetVersionStatusHelpResponse> getVersionStatus(GetVersionStatusHelpRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, developerWebService::getVersionStatus);
    }
}
