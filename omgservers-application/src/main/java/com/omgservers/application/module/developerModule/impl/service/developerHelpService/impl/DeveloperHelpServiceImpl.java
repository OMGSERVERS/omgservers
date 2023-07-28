package com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl;

import com.omgservers.application.module.developerModule.impl.service.developerHelpService.DeveloperHelpService;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createProjectMethod.CreateProjectMethod;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createTokenMethod.CreateTokenMethod;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.createVersionMethod.CreateVersionMethod;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.impl.method.getVersionStatusOperation.GetVersionStatusMethod;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateProjectHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateTokenHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.CreateVersionHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.request.GetVersionStatusHelpRequest;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateProjectHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateTokenHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.CreateVersionHelpResponse;
import com.omgservers.application.module.developerModule.impl.service.developerHelpService.response.GetVersionStatusHelpResponse;
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
    public Uni<CreateTokenHelpResponse> createToken(CreateTokenHelpRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<CreateProjectHelpResponse> createProject(CreateProjectHelpRequest request) {
        return createProjectMethod.createProject(request);
    }

    @Override
    public Uni<CreateVersionHelpResponse> createVersion(CreateVersionHelpRequest request) {
        return createVersionMethod.createVersion(request);
    }

    @Override
    public Uni<GetVersionStatusHelpResponse> getVersionStatus(GetVersionStatusHelpRequest request) {
        return getVersionStatusMethod.getVersionStatus(request);
    }
}
