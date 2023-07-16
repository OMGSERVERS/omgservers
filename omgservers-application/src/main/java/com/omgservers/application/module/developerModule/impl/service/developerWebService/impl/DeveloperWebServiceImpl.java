package com.omgservers.application.module.developerModule.impl.service.developerWebService.impl;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.DeveloperHelpService;
import com.omgservers.application.module.developerModule.impl.service.developerWebService.DeveloperWebService;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateTokenHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperWebServiceImpl implements DeveloperWebService {

    final DeveloperHelpService developerHelpService;

    @Override
    public Uni<CreateTokenHelpResponse> createToken(CreateTokenHelpRequest request) {
        return developerHelpService.createToken(request);
    }

    @Override
    public Uni<CreateProjectHelpResponse> createProject(CreateProjectHelpRequest request) {
        return developerHelpService.createProject(request);
    }

    @Override
    public Uni<CreateVersionHelpResponse> createVersion(CreateVersionHelpRequest request) {
        return developerHelpService.createVersion(request);
    }

    @Override
    public Uni<GetVersionStatusHelpResponse> getVersionStatus(GetVersionStatusHelpRequest request) {
        return developerHelpService.getVersionStatus(request);
    }
}
