package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl;

import com.omgservers.schema.entrypoint.developer.*;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.*;
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
    final CreateTenantImageMethod createTenantImageMethod;
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
    public Uni<CreateTenantImageDeveloperResponse> execute(@Valid final CreateTenantImageDeveloperRequest request) {
        return createTenantImageMethod.execute(request);
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
