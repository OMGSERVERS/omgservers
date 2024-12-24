package com.omgservers.service.entrypoint.developer.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectAliasDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantStageAliasDeveloperResponse;
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
    public Uni<CreateTokenDeveloperResponse> execute(final CreateTokenDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> execute(
            final GetTenantDashboardDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectDeveloperResponse> execute(
            final CreateTenantProjectDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectAliasDeveloperResponse> execute(
            final CreateTenantProjectAliasDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetTenantProjectDashboardDeveloperResponse> execute(
            final GetTenantProjectDashboardDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> execute(
            final DeleteTenantProjectDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateTenantStageDeveloperResponse> execute(final CreateTenantStageDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateTenantStageAliasDeveloperResponse> execute(final CreateTenantStageAliasDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetTenantStageDashboardDeveloperResponse> execute(
            final GetTenantStageDashboardDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteTenantStageDeveloperResponse> execute(final DeleteTenantStageDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateTenantVersionDeveloperResponse> execute(
            final CreateTenantVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<UploadFilesArchiveDeveloperResponse> execute(
            final UploadFilesArchiveDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetTenantVersionDashboardDeveloperResponse> execute(
            final GetTenantVersionDashboardDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> execute(
            final DeleteTenantVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeployTenantVersionDeveloperResponse> execute(
            final DeployTenantVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetTenantDeploymentDashboardDeveloperResponse> execute(
            final GetTenantDeploymentDashboardDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteTenantDeploymentDeveloperResponse> execute(
            final DeleteTenantDeploymentDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateLobbyRequestDeveloperResponse> execute(
            final CreateLobbyRequestDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteLobbyDeveloperResponse> execute(final DeleteLobbyDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateMatchmakerRequestDeveloperResponse> execute(
            final CreateMatchmakerRequestDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerDeveloperResponse> execute(final DeleteMatchmakerDeveloperRequest request) {
        return developerService.execute(request);
    }
}
