package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl;

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
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateLobbyRequestMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateMatchmakerRequestMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantProjectAliasMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantProjectMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantStageAliasMethod;
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
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantDeploymentDetailsMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantDetailsMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantProjectDetailsMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantStageDetailsMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantVersionDetailsMethod;
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

    final GetTenantDeploymentDetailsMethod getTenantDeploymentDetailsMethod;
    final CreateTenantProjectAliasMethod createTenantProjectAliasMethod;
    final GetTenantVersionDetailsMethod getTenantVersionDetailsMethod;
    final GetTenantProjectDetailsMethod getTenantProjectDetailsMethod;
    final CreateMatchmakerRequestMethod createMatchmakerRequestMethod;
    final DeleteTenantDeploymentMethod deleteTenantDeploymentMethod;
    final CreateTenantStageAliasMethod createTenantStageAliasMethod;
    final GetTenantStageDetailsMethod getTenantStageDetailsMethod;
    final CreateTenantVersionMethod createTenantVersionMethod;
    final CreateTenantProjectMethod createTenantProjectMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final DeleteTenantVersionMethod deleteTenantVersionMethod;
    final DeployTenantVersionMethod deployTenantVersionMethod;
    final UploadFilesArchiveMethod uploadFilesArchiveMethod;
    final CreateLobbyRequestMethod createLobbyRequestMethod;
    final DeleteTenantStageMethod deleteTenantStageMethod;
    final CreateTenantStageMethod createTenantStageMethod;
    final GetTenantDetailsMethod getTenantDetailsMethod;
    final DeleteMatchmakerMethod deleteMatchmakerMethod;
    final CreateTokenMethod createTokenMethod;
    final DeleteLobbyMethod deleteLobbyMethod;

    @Override
    public Uni<CreateTokenDeveloperResponse> execute(@Valid final CreateTokenDeveloperRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<GetTenantDetailsDeveloperResponse> execute(
            @Valid final GetTenantDetailsDeveloperRequest request) {
        return getTenantDetailsMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectDeveloperResponse> execute(
            @Valid final CreateTenantProjectDeveloperRequest request) {
        return createTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectAliasDeveloperResponse> execute(
            @Valid final CreateTenantProjectAliasDeveloperRequest request) {
        return createTenantProjectAliasMethod.execute(request);
    }

    @Override
    public Uni<GetTenantProjectDetailsDeveloperResponse> execute(
            @Valid final GetTenantProjectDetailsDeveloperRequest request) {
        return getTenantProjectDetailsMethod.execute(request);
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
    public Uni<CreateTenantStageAliasDeveloperResponse> execute(
            @Valid final CreateTenantStageAliasDeveloperRequest request) {
        return createTenantStageAliasMethod.execute(request);
    }

    @Override
    public Uni<GetTenantStageDetailsDeveloperResponse> execute(
            @Valid final GetTenantStageDetailsDeveloperRequest request) {
        return getTenantStageDetailsMethod.execute(request);
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
    public Uni<UploadFilesArchiveDeveloperResponse> execute(@Valid final UploadFilesArchiveDeveloperRequest request) {
        return uploadFilesArchiveMethod.execute(request);
    }

    @Override
    public Uni<GetTenantVersionDetailsDeveloperResponse> execute(
            @Valid final GetTenantVersionDetailsDeveloperRequest request) {
        return getTenantVersionDetailsMethod.execute(request);
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
    public Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(
            @Valid final GetTenantDeploymentDetailsDeveloperRequest request) {
        return getTenantDeploymentDetailsMethod.execute(request);
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
