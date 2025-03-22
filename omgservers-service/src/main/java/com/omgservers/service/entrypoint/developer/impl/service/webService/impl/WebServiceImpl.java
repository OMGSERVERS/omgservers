package com.omgservers.service.entrypoint.developer.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.developer.*;
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
    public Uni<GetTenantDetailsDeveloperResponse> execute(
            final GetTenantDetailsDeveloperRequest request) {
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
    public Uni<GetTenantProjectDetailsDeveloperResponse> execute(
            final GetTenantProjectDetailsDeveloperRequest request) {
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
    public Uni<GetTenantStageDetailsDeveloperResponse> execute(
            final GetTenantStageDetailsDeveloperRequest request) {
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
    public Uni<UploadFilesArchiveDeveloperResponse> execute(final UploadFilesArchiveDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetTenantVersionDetailsDeveloperResponse> execute(
            final GetTenantVersionDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> execute(
            final DeleteTenantVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateTenantImageDeveloperResponse> execute(final CreateTenantImageDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeployTenantVersionDeveloperResponse> execute(
            final DeployTenantVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(
            final GetTenantDeploymentDetailsDeveloperRequest request) {
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
