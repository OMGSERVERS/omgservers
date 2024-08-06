package com.omgservers.service.entrypoint.developer.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.UploadVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadVersionDeveloperResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.entrypoint.developer.impl.service.webService.WebService;
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
    public Uni<GetStageDashboardDeveloperResponse> getStageDashboard(final GetStageDashboardDeveloperRequest request) {
        return developerService.getStageDashboard(request);
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
    public Uni<GetVersionDashboardDeveloperResponse> getVersionDashboard(
            final GetVersionDashboardDeveloperRequest request) {
        return developerService.getVersionDashboard(request);
    }

    @Override
    public Uni<UploadVersionDeveloperResponse> uploadVersion(final UploadVersionDeveloperRequest request) {
        return developerService.uploadVersion(request);
    }

    @Override
    public Uni<DeployVersionDeveloperResponse> deployVersion(final DeployVersionDeveloperRequest request) {
        return developerService.deployVersion(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> deleteVersion(final DeleteVersionDeveloperRequest request) {
        return developerService.deleteVersion(request);
    }
}
