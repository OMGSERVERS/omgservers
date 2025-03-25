package com.omgservers.service.entrypoint.developer.impl.service.developerService;

import com.omgservers.schema.entrypoint.developer.*;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DeveloperService {

    Uni<CreateTokenDeveloperResponse> execute(@Valid CreateTokenDeveloperRequest request);

    Uni<GetTenantDetailsDeveloperResponse> execute(@Valid GetTenantDetailsDeveloperRequest request);

    Uni<CreateProjectDeveloperResponse> execute(@Valid CreateProjectDeveloperRequest request);

    Uni<CreateProjectAliasDeveloperResponse> execute(@Valid CreateProjectAliasDeveloperRequest request);

    Uni<GetProjectDetailsDeveloperResponse> execute(@Valid GetProjectDetailsDeveloperRequest request);

    Uni<DeleteProjectDeveloperResponse> execute(@Valid DeleteProjectDeveloperRequest request);

    Uni<CreateStageDeveloperResponse> execute(@Valid CreateStageDeveloperRequest request);

    Uni<CreateStageAliasDeveloperResponse> execute(@Valid CreateStageAliasDeveloperRequest request);

    Uni<GetStageDetailsDeveloperResponse> execute(@Valid GetStageDetailsDeveloperRequest request);

    Uni<DeleteStageDeveloperResponse> execute(@Valid DeleteStageDeveloperRequest request);

    Uni<CreateVersionDeveloperResponse> execute(@Valid CreateVersionDeveloperRequest request);

    Uni<GetVersionDetailsDeveloperResponse> execute(@Valid GetVersionDetailsDeveloperRequest request);

    Uni<DeleteVersionDeveloperResponse> execute(@Valid DeleteVersionDeveloperRequest request);

    Uni<CreateImageDeveloperResponse> execute(@Valid CreateImageDeveloperRequest request);

    /*
    Tenant deployment
     */

    Uni<CreateDeploymentDeveloperResponse> execute(@Valid CreateDeploymentDeveloperRequest request);

    Uni<GetDeploymentDetailsDeveloperResponse> execute(@Valid GetDeploymentDetailsDeveloperRequest request);

    Uni<DeleteDeploymentDeveloperResponse> execute(@Valid DeleteDeploymentDeveloperRequest request);

}
