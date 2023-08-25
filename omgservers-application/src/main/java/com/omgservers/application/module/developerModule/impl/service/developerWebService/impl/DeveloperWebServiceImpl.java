package com.omgservers.application.module.developerModule.impl.service.developerWebService.impl;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.DeveloperHelpService;
import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import com.omgservers.dto.developerModule.CreateTokenDeveloperResponse;
import com.omgservers.dto.developerModule.CreateVersionDeveloperResponse;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperResponse;
import com.omgservers.application.module.developerModule.impl.service.developerWebService.DeveloperWebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperWebServiceImpl implements DeveloperWebService {

    final DeveloperHelpService developerHelpService;

    @Override
    public Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request) {
        return developerHelpService.createToken(request);
    }

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request) {
        return developerHelpService.createProject(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request) {
        return developerHelpService.createVersion(request);
    }

    @Override
    public Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request) {
        return developerHelpService.getVersionStatus(request);
    }
}
