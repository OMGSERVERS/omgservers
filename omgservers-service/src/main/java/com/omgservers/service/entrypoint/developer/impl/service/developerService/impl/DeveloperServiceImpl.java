package com.omgservers.service.entrypoint.developer.impl.service.developerService.impl;

import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateImageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectAliasDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageAliasDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTokenDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteDeploymentDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteProjectDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteStageDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.DeleteVersionDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetProjectDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetStageDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetVersionDetailsDeveloperResponse;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.DeveloperService;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantImageMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantProjectAliasMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantProjectMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantStageAliasMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantStageMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTenantVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteTenantProjectMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteTenantStageMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.DeleteTenantVersionMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantDetailsMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantProjectDetailsMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantStageDetailsMethod;
import com.omgservers.service.entrypoint.developer.impl.service.developerService.impl.method.GetTenantVersionDetailsMethod;
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
    public Uni<GetTenantDetailsDeveloperResponse> execute(@Valid final GetTenantDetailsDeveloperRequest request) {
        return getTenantDetailsMethod.execute(request);
    }

    @Override
    public Uni<CreateProjectDeveloperResponse> execute(@Valid final CreateProjectDeveloperRequest request) {
        return createTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateProjectAliasDeveloperResponse> execute(@Valid final CreateProjectAliasDeveloperRequest request) {
        return createTenantProjectAliasMethod.execute(request);
    }

    @Override
    public Uni<GetProjectDetailsDeveloperResponse> execute(@Valid final GetProjectDetailsDeveloperRequest request) {
        return getTenantProjectDetailsMethod.execute(request);
    }

    @Override
    public Uni<DeleteProjectDeveloperResponse> execute(@Valid final DeleteProjectDeveloperRequest request) {
        return deleteTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateStageDeveloperResponse> execute(@Valid final CreateStageDeveloperRequest request) {
        return createTenantStageMethod.execute(request);
    }

    @Override
    public Uni<CreateStageAliasDeveloperResponse> execute(@Valid final CreateStageAliasDeveloperRequest request) {
        return createTenantStageAliasMethod.execute(request);
    }

    @Override
    public Uni<GetStageDetailsDeveloperResponse> execute(@Valid final GetStageDetailsDeveloperRequest request) {
        return getTenantStageDetailsMethod.execute(request);
    }

    @Override
    public Uni<DeleteStageDeveloperResponse> execute(
            @Valid final DeleteStageDeveloperRequest request) {
        return deleteTenantStageMethod.execute(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> execute(@Valid final CreateVersionDeveloperRequest request) {
        return createTenantVersionMethod.execute(request);
    }

    @Override
    public Uni<GetVersionDetailsDeveloperResponse> execute(@Valid final GetVersionDetailsDeveloperRequest request) {
        return getTenantVersionDetailsMethod.execute(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> execute(@Valid final DeleteVersionDeveloperRequest request) {
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
