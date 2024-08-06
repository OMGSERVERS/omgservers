package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl;

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
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createProject.CreateProjectMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.createVersion.CreateVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployVersion.DeployVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getStageDashboard.GetStageDashboardMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getTenantDashboard.GetTenantDashboardMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.getVersionDashboard.GetVersionDashboardMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.uploadVersion.UploadVersionMethod;
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

    final GetVersionDashboardMethod getVersionDashboardMethod;
    final GetTenantDashboardMethod getTenantDashboardMethod;
    final GetStageDashboardMethod getStageDashboardMethod;
    final CreateVersionMethod createVersionMethod;
    final CreateProjectMethod createProjectMethod;
    final UploadVersionMethod uploadVersionMethod;
    final DeleteVersionMethod deleteVersionMethod;
    final DeployVersionMethod deployVersionMethod;
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
    public Uni<GetStageDashboardDeveloperResponse> getStageDashboard(
            @Valid final GetStageDashboardDeveloperRequest request) {
        return getStageDashboardMethod.getStageDashboard(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> createVersion(@Valid final CreateVersionDeveloperRequest request) {
        return createVersionMethod.createVersion(request);
    }

    @Override
    public Uni<GetVersionDashboardDeveloperResponse> getVersionDashboard(
            @Valid final GetVersionDashboardDeveloperRequest request) {
        return getVersionDashboardMethod.getVersionDashboard(request);
    }

    @Override
    public Uni<UploadVersionDeveloperResponse> uploadVersion(@Valid final UploadVersionDeveloperRequest request) {
        return uploadVersionMethod.uploadVersion(request);
    }

    @Override
    public Uni<DeployVersionDeveloperResponse> deployVersion(@Valid final DeployVersionDeveloperRequest request) {
        return deployVersionMethod.deployVersion(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> deleteVersion(@Valid final DeleteVersionDeveloperRequest request) {
        return deleteVersionMethod.deleteVersion(request);
    }
}
