package com.omgservers.service.entrypoint.developer.impl.service.webService.impl;

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

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantDetailsDeveloperResponse> execute(final GetTenantDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    /*
    Tenant project
     */

    @Override
    public Uni<CreateProjectDeveloperResponse> execute(final CreateProjectDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateProjectAliasDeveloperResponse> execute(final CreateProjectAliasDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetProjectDetailsDeveloperResponse> execute(final GetProjectDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteProjectDeveloperResponse> execute(final DeleteProjectDeveloperRequest request) {
        return developerService.execute(request);
    }

    /*
    Tenant stage
     */

    @Override
    public Uni<CreateStageDeveloperResponse> execute(final CreateStageDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateStageAliasDeveloperResponse> execute(final CreateStageAliasDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetStageDetailsDeveloperResponse> execute(final GetStageDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteStageDeveloperResponse> execute(final DeleteStageDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<CreateVersionDeveloperResponse> execute(final CreateVersionDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<GetVersionDetailsDeveloperResponse> execute(final GetVersionDetailsDeveloperRequest request) {
        return developerService.execute(request);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> execute(final DeleteVersionDeveloperRequest request) {
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
