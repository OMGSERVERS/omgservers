package com.omgservers.service.module.developer.impl.service.developerService.impl;

import com.omgservers.model.dto.developer.CreateProjectDeveloperRequest;
import com.omgservers.model.dto.developer.CreateProjectDeveloperResponse;
import com.omgservers.model.dto.developer.CreateTokenDeveloperRequest;
import com.omgservers.model.dto.developer.CreateTokenDeveloperResponse;
import com.omgservers.model.dto.developer.CreateVersionDeveloperRequest;
import com.omgservers.model.dto.developer.CreateVersionDeveloperResponse;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperRequest;
import com.omgservers.model.dto.developer.DeleteVersionDeveloperResponse;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.model.dto.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.service.module.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.createProject.CreateProjectMethod;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.createVersion.CreateVersionMethod;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.service.module.developer.impl.service.developerService.impl.method.getTenantDashboard.GetTenantDashboardMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeveloperServiceImpl implements DeveloperService {

    final GetTenantDashboardMethod getTenantDashboardMethod;
    final CreateProjectMethod createProjectMethod;
    final CreateVersionMethod createVersionMethod;
    final DeleteVersionMethod deleteVersionMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenDeveloperResponse> createToken(@Valid final CreateTokenDeveloperRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            @Valid final GetTenantDashboardDeveloperRequest request) {
        return getTenantDashboardMethod.getTenantDashboard(request);
    }

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(@Valid final CreateProjectDeveloperRequest request) {
        return createProjectMethod.createProject(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(@Valid final CreateVersionDeveloperRequest request) {
        return createVersionMethod.createVersion(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> deleteVersion(@Valid final DeleteVersionDeveloperRequest request) {
        return deleteVersionMethod.deleteVersion(request);
    }
}
