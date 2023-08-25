package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.DeveloperHelpService;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createProjectMethod.CreateProjectMethod;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createTokenMethod.CreateTokenMethod;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createVersionMethod.CreateVersionMethod;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.getVersionStatusOperation.GetVersionStatusMethod;
import com.omgservers.dto.developerModule.CreateProjectDeveloperRequest;
import com.omgservers.dto.developerModule.CreateTokenDeveloperRequest;
import com.omgservers.dto.developerModule.CreateVersionDeveloperRequest;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperRequest;
import com.omgservers.dto.developerModule.CreateProjectDeveloperResponse;
import com.omgservers.dto.developerModule.CreateTokenDeveloperResponse;
import com.omgservers.dto.developerModule.CreateVersionDeveloperResponse;
import com.omgservers.dto.developerModule.GetVersionStatusDeveloperResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperHelpServiceImpl implements DeveloperHelpService {

    final GetVersionStatusMethod getVersionStatusMethod;
    final CreateProjectMethod createProjectMethod;
    final CreateVersionMethod createVersionMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request) {
        return createProjectMethod.createProject(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request) {
        return createVersionMethod.createVersion(request);
    }

    @Override
    public Uni<GetVersionStatusDeveloperResponse> getVersionStatus(GetVersionStatusDeveloperRequest request) {
        return getVersionStatusMethod.getVersionStatus(request);
    }
}
