package com.omgservers.module.developer.impl.service.webService.impl;

import com.omgservers.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.module.developer.impl.service.developerService.DeveloperService;
import com.omgservers.module.developer.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final DeveloperService developerService;

    @Override
    public Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request) {
        return developerService.createToken(request);
    }

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(CreateProjectDeveloperRequest request) {
        return developerService.createProject(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(CreateVersionDeveloperRequest request) {
        return developerService.createVersion(request);
    }
}
