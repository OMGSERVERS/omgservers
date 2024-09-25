package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl;

import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.BuildTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeployTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.BuildTenantVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantProjectMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantStageMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteTenantDeploymentMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteTenantProjectMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteTenantStageMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteTenantVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeployTenantVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantDashboardMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantDeploymentDashboardMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantProjectDashboardMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantStageDashboardMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantVersionDashboardMethod;
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

    final GetTenantDeploymentDashboardMethod getTenantDeploymentDashboardMethod;
    final GetTenantVersionDashboardMethod getTenantVersionDashboardMethod;
    final GetTenantProjectDashboardMethod getTenantProjectDashboardMethod;
    final GetTenantStageDashboardMethod getTenantStageDashboardMethod;
    final DeleteTenantDeploymentMethod deleteTenantDeploymentMethod;
    final CreateTenantVersionMethod createTenantVersionMethod;
    final CreateTenantProjectMethod createTenantProjectMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final DeleteTenantVersionMethod deleteTenantVersionMethod;
    final DeployTenantVersionMethod deployTenantVersionMethod;
    final BuildTenantVersionMethod buildTenantVersionMethod;

    final DeleteTenantStageMethod deleteTenantStageMethod;

    final GetTenantDashboardMethod getTenantDashboardMethod;
    final CreateTenantStageMethod createTenantStageMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenDeveloperResponse> createToken(@Valid final CreateTokenDeveloperRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            @Valid final GetTenantDashboardDeveloperRequest request) {
        return getTenantDashboardMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectDeveloperResponse> createTenantProject(
            @Valid final CreateTenantProjectDeveloperRequest request) {
        return createTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<GetTenantProjectDashboardDeveloperResponse> getTenantProjectDashboard(
            @Valid final GetTenantProjectDashboardDeveloperRequest request) {
        return getTenantProjectDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> deleteTenantProject(
            @Valid final DeleteTenantProjectDeveloperRequest request) {
        return deleteTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantStageDeveloperResponse> createTenantStage(
            @Valid final CreateTenantStageDeveloperRequest request) {
        return createTenantStageMethod.execute(request);
    }

    @Override
    public Uni<GetTenantStageDashboardDeveloperResponse> getTenantStageDashboard(
            @Valid final GetTenantStageDashboardDeveloperRequest request) {
        return getTenantStageDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantStageDeveloperResponse> deleteTenantStage(
            @Valid final DeleteTenantStageDeveloperRequest request) {
        return deleteTenantStageMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantVersionDeveloperResponse> createTenantVersion(
            @Valid final CreateTenantVersionDeveloperRequest request) {
        return createTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<BuildTenantVersionDeveloperResponse> buildTenantVersion(
            @Valid final BuildTenantVersionDeveloperRequest request) {
        return buildTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<GetTenantVersionDashboardDeveloperResponse> getTenantVersionDashboard(
            @Valid final GetTenantVersionDashboardDeveloperRequest request) {
        return getTenantVersionDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> deleteTenantVersion(
            @Valid final DeleteTenantVersionDeveloperRequest request) {
        return deleteTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<DeployTenantVersionDeveloperResponse> deployTenantVersion(
            @Valid final DeployTenantVersionDeveloperRequest request) {
        return deployTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<GetTenantDeploymentDashboardDeveloperResponse> getTenantDeploymentDashboard(
            @Valid final GetTenantDeploymentDashboardDeveloperRequest request) {
        return getTenantDeploymentDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantDeploymentDeveloperResponse> deleteTenantDeployment(
            @Valid final DeleteTenantDeploymentDeveloperRequest request) {
        return deleteTenantDeploymentMethod.execute(request);
    }
}
