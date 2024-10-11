package com.omgservers.service.entrypoint.developer.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteLobbyDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteLobbyDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteMatchmakerDeveloperResponse;
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
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
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
    public Uni<CreateTenantProjectDeveloperResponse> createTenantProject(
            final CreateTenantProjectDeveloperRequest request) {
        return developerService.createTenantProject(request);
    }

    @Override
    public Uni<GetTenantProjectDashboardDeveloperResponse> getTenantProjectDashboard(
            final GetTenantProjectDashboardDeveloperRequest request) {
        return developerService.getTenantProjectDashboard(request);
    }

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> deleteTenantProject(
            final DeleteTenantProjectDeveloperRequest request) {
        return developerService.deleteTenantProject(request);
    }

    @Override
    public Uni<CreateTenantStageDeveloperResponse> createTenantStage(final CreateTenantStageDeveloperRequest request) {
        return developerService.createTenantStage(request);
    }

    @Override
    public Uni<GetTenantStageDashboardDeveloperResponse> getTenantStageDashboard(
            final GetTenantStageDashboardDeveloperRequest request) {
        return developerService.getTenantStageDashboard(request);
    }

    @Override
    public Uni<DeleteTenantStageDeveloperResponse> deleteTenantStage(final DeleteTenantStageDeveloperRequest request) {
        return developerService.deleteTenantStage(request);
    }

    @Override
    public Uni<CreateTenantVersionDeveloperResponse> createTenantVersion(
            final CreateTenantVersionDeveloperRequest request) {
        return developerService.createTenantVersion(request);
    }

    @Override
    public Uni<UploadFilesArchiveDeveloperResponse> uploadFilesArchive(
            final UploadFilesArchiveDeveloperRequest request) {
        return developerService.uploadFilesArchive(request);
    }

    @Override
    public Uni<GetTenantVersionDashboardDeveloperResponse> getTenantVersionDashboard(
            final GetTenantVersionDashboardDeveloperRequest request) {
        return developerService.getTenantVersionDashboard(request);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> deleteTenantVersion(
            final DeleteTenantVersionDeveloperRequest request) {
        return developerService.deleteTenantVersion(request);
    }

    @Override
    public Uni<DeployTenantVersionDeveloperResponse> deployTenantVersion(
            final DeployTenantVersionDeveloperRequest request) {
        return developerService.deployTenantVersion(request);
    }

    @Override
    public Uni<GetTenantDeploymentDashboardDeveloperResponse> getTenantDeploymentDashboard(
            final GetTenantDeploymentDashboardDeveloperRequest request) {
        return developerService.getTenantDeploymentDashboard(request);
    }

    @Override
    public Uni<DeleteTenantDeploymentDeveloperResponse> deleteTenantDeployment(
            final DeleteTenantDeploymentDeveloperRequest request) {
        return developerService.deleteTenantDeployment(request);
    }

    @Override
    public Uni<DeleteLobbyDeveloperResponse> deleteLobby(final DeleteLobbyDeveloperRequest request) {
        return developerService.deleteLobby(request);
    }

    @Override
    public Uni<DeleteMatchmakerDeveloperResponse> deleteMatchmaker(final DeleteMatchmakerDeveloperRequest request) {
        return developerService.deleteMatchmaker(request);
    }
}
