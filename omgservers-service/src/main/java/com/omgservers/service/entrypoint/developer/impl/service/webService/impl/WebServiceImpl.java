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
    public Uni<CreateProjectDeveloperResponse> execute(
            final CreateProjectDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateProjectAliasDeveloperResponse> execute(
            final CreateProjectAliasDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetProjectDetailsDeveloperResponse> execute(
            final GetProjectDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteProjectDeveloperResponse> execute(
            final DeleteProjectDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateStageDeveloperResponse> execute(final CreateStageDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateStageAliasDeveloperResponse> execute(final CreateStageAliasDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetStageDetailsDeveloperResponse> execute(
            final GetStageDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteStageDeveloperResponse> execute(final DeleteStageDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> execute(
            final CreateVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetVersionDetailsDeveloperResponse> execute(
            final GetVersionDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> execute(
            final DeleteVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateImageDeveloperResponse> execute(final CreateImageDeveloperRequest request) {
        return developerService.execute(request);
    }

    /*
    Tenant deployment
     */

    @Override
    public Uni<CreateDeploymentDeveloperResponse> execute(final CreateDeploymentDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetDeploymentDetailsDeveloperResponse> execute(final GetDeploymentDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentDeveloperResponse> execute(final DeleteDeploymentDeveloperRequest request) {
        return developerService.execute(request);
    }
}
