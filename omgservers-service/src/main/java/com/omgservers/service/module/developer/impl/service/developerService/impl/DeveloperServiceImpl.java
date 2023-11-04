package com.omgservers.service.module.developer.impl.service.developerService.impl;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.service.module.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.createProject.CreateProjectMethod;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.createVersion.CreateVersionMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperServiceImpl implements DeveloperService {

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
}