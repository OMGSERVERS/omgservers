package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl;

import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateLobbyRequestDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateMatchmakerRequestDeveloperResponse;
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
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateLobbyRequestMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateMatchmakerRequestMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantProjectMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantStageMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteLobbyMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteMatchmakerMethod;
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
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.UploadFilesArchiveMethod;
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
    final CreateMatchmakerRequestMethod createMatchmakerRequestMethod;
    final DeleteTenantDeploymentMethod deleteTenantDeploymentMethod;
    final CreateTenantVersionMethod createTenantVersionMethod;
    final CreateTenantProjectMethod createTenantProjectMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final DeleteTenantVersionMethod deleteTenantVersionMethod;
    final DeployTenantVersionMethod deployTenantVersionMethod;
    final UploadFilesArchiveMethod uploadFilesArchiveMethod;
    final GetTenantDashboardMethod getTenantDashboardMethod;
    final CreateLobbyRequestMethod createLobbyRequestMethod;
    final DeleteTenantStageMethod deleteTenantStageMethod;
    final CreateTenantStageMethod createTenantStageMethod;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final CreateTokenMethod createTokenMethod;
    final DeleteLobbyMethod deleteLobbyMethod;

    @Override
    public Uni<CreateTokenDeveloperResponse> execute(@Valid final CreateTokenDeveloperRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> execute(
            @Valid final GetTenantDashboardDeveloperRequest request) {
        return getTenantDashboardMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectDeveloperResponse> execute(
            @Valid final CreateTenantProjectDeveloperRequest request) {
        return createTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<GetTenantProjectDashboardDeveloperResponse> execute(
            @Valid final GetTenantProjectDashboardDeveloperRequest request) {
        return getTenantProjectDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> execute(
            @Valid final DeleteTenantProjectDeveloperRequest request) {
        return deleteTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantStageDeveloperResponse> execute(
            @Valid final CreateTenantStageDeveloperRequest request) {
        return createTenantStageMethod.execute(request);
    }

    @Override
    public Uni<GetTenantStageDashboardDeveloperResponse> execute(
            @Valid final GetTenantStageDashboardDeveloperRequest request) {
        return getTenantStageDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantStageDeveloperResponse> execute(
            @Valid final DeleteTenantStageDeveloperRequest request) {
        return deleteTenantStageMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantVersionDeveloperResponse> execute(
            @Valid final CreateTenantVersionDeveloperRequest request) {
        return createTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<UploadFilesArchiveDeveloperResponse> execute(
            @Valid final UploadFilesArchiveDeveloperRequest request) {
        return uploadFilesArchiveMethod.execute(request);
    }

    @Override
    public Uni<GetTenantVersionDashboardDeveloperResponse> execute(
            @Valid final GetTenantVersionDashboardDeveloperRequest request) {
        return getTenantVersionDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> execute(
            @Valid final DeleteTenantVersionDeveloperRequest request) {
        return deleteTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<DeployTenantVersionDeveloperResponse> execute(
            @Valid final DeployTenantVersionDeveloperRequest request) {
        return deployTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<GetTenantDeploymentDashboardDeveloperResponse> execute(
            @Valid final GetTenantDeploymentDashboardDeveloperRequest request) {
        return getTenantDeploymentDashboardMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantDeploymentDeveloperResponse> execute(
            @Valid final DeleteTenantDeploymentDeveloperRequest request) {
        return deleteTenantDeploymentMethod.execute(request);
    }

    @Override
    public Uni<CreateLobbyRequestDeveloperResponse> execute(
            @Valid final CreateLobbyRequestDeveloperRequest request) {
        return createLobbyRequestMethod.execute(request);
    }

    @Override
    public Uni<DeleteLobbyDeveloperResponse> execute(@Valid final DeleteLobbyDeveloperRequest request) {
        return deleteLobbyMethod.execute(request);
    }

    @Override
    public Uni<CreateMatchmakerRequestDeveloperResponse> execute(
            @Valid final CreateMatchmakerRequestDeveloperRequest request) {
        return createMatchmakerRequestMethod.execute(request);
    }

    @Override
    public Uni<DeleteMatchmakerDeveloperResponse> execute(
            @Valid final DeleteMatchmakerDeveloperRequest request) {
        return deleteMatchmakerMethod.execute(request);
    }
}
