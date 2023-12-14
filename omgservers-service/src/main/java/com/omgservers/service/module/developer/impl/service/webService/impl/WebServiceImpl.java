package com.omgservers.service.module.developer.impl.service.webService.impl;

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
import com.omgservers.service.module.developer.impl.service.webService.WebService;
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
    public Uni<CreateTokenDeveloperResponse> createToken(final CreateTokenDeveloperRequest request) {
        return developerService.createToken(request);
    }

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            final GetTenantDashboardDeveloperRequest request) {
        return developerService.getTenantDashboard(request);
    }

    @Override
    public Uni<CreateProjectDeveloperResponse> createProject(final CreateProjectDeveloperRequest request) {
        return developerService.createProject(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(final CreateVersionDeveloperRequest request) {
        return developerService.createVersion(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> deleteVersion(final DeleteVersionDeveloperRequest request) {
        return developerService.deleteVersion(request);
    }
}
