package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl;

import com.omgservers.schema.entrypoint.developer.*;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.*;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment.CreateDeploymentMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment.DeleteDeploymentMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.deployment.GetDeploymentDetailsMethod;
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

    final GetDeploymentDetailsMethod getDeploymentDetailsMethod;
    final CreateTenantProjectAliasMethod createTenantProjectAliasMethod;
    final GetTenantVersionDetailsMethod getTenantVersionDetailsMethod;
    final GetTenantProjectDetailsMethod getTenantProjectDetailsMethod;
    final DeleteDeploymentMethod deleteDeploymentMethod;
    final CreateTenantStageAliasMethod createTenantStageAliasMethod;
    final GetTenantStageDetailsMethod getTenantStageDetailsMethod;
    final CreateTenantVersionMethod createTenantVersionMethod;
    final CreateTenantProjectMethod createTenantProjectMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final DeleteTenantVersionMethod deleteTenantVersionMethod;
    final CreateDeploymentMethod createDeploymentMethod;
    final CreateTenantImageMethod createTenantImageMethod;
    final DeleteTenantStageMethod deleteTenantStageMethod;
    final CreateTenantStageMethod createTenantStageMethod;
    final GetTenantDetailsMethod getTenantDetailsMethod;
    final CreateTokenMethod createTokenMethod;

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
    public Uni<CreateProjectDeveloperResponse> execute(
            @Valid final CreateProjectDeveloperRequest request) {
        return createTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateProjectAliasDeveloperResponse> execute(
            @Valid final CreateProjectAliasDeveloperRequest request) {
        return createTenantProjectAliasMethod.execute(request);
    }

    @Override
    public Uni<GetProjectDetailsDeveloperResponse> execute(
            @Valid final GetProjectDetailsDeveloperRequest request) {
        return getTenantProjectDetailsMethod.execute(request);
    }

    @Override
    public Uni<DeleteProjectDeveloperResponse> execute(
            @Valid final DeleteProjectDeveloperRequest request) {
        return deleteTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateStageDeveloperResponse> execute(
            @Valid final CreateStageDeveloperRequest request) {
        return createTenantStageMethod.execute(request);
    }

    @Override
    public Uni<CreateStageAliasDeveloperResponse> execute(
            @Valid final CreateStageAliasDeveloperRequest request) {
        return createTenantStageAliasMethod.execute(request);
    }

    @Override
    public Uni<GetStageDetailsDeveloperResponse> execute(
            @Valid final GetStageDetailsDeveloperRequest request) {
        return getTenantStageDetailsMethod.execute(request);
    }

    @Override
    public Uni<DeleteStageDeveloperResponse> execute(
            @Valid final DeleteStageDeveloperRequest request) {
        return deleteTenantStageMethod.execute(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> execute(
            @Valid final CreateVersionDeveloperRequest request) {
        return createTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<GetVersionDetailsDeveloperResponse> execute(
            @Valid final GetVersionDetailsDeveloperRequest request) {
        return getTenantVersionDetailsMethod.execute(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> execute(
            @Valid final DeleteVersionDeveloperRequest request) {
        return deleteTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<CreateImageDeveloperResponse> execute(@Valid final CreateImageDeveloperRequest request) {
        return createTenantImageMethod.execute(request);
    }

    /*
    Tenant deployment
     */

    @Override
    public Uni<CreateDeploymentDeveloperResponse> execute(@Valid final CreateDeploymentDeveloperRequest request) {
        return createDeploymentMethod.execute(request);
    }

    @Override
    public Uni<GetDeploymentDetailsDeveloperResponse> execute(@Valid final GetDeploymentDetailsDeveloperRequest request) {
        return getDeploymentDetailsMethod.execute(request);
    }

    @Override
    public Uni<DeleteDeploymentDeveloperResponse> execute(@Valid final DeleteDeploymentDeveloperRequest request) {
        return deleteDeploymentMethod.execute(request);
    }
}
