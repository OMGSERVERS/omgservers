package com.omgservers.service.entrypoint.developer.impl.service.webService;

import com.omgservers.schema.entrypoint.developer.*;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenDeveloperResponse> execute(CreateTokenDeveloperRequest request);

    /*
    Tenant
     */

    Uni<GetTenantDetailsDeveloperResponse> execute(GetTenantDetailsDeveloperRequest request);

    /*
    Tenant project
     */

    Uni<CreateProjectDeveloperResponse> execute(CreateProjectDeveloperRequest request);

    Uni<CreateProjectAliasDeveloperResponse> execute(CreateProjectAliasDeveloperRequest request);

    Uni<GetProjectDetailsDeveloperResponse> execute(GetProjectDetailsDeveloperRequest request);

    Uni<DeleteProjectDeveloperResponse> execute(DeleteProjectDeveloperRequest request);

    /*
    Tenant stage
     */

    Uni<CreateStageDeveloperResponse> execute(CreateStageDeveloperRequest request);

    Uni<CreateStageAliasDeveloperResponse> execute(CreateStageAliasDeveloperRequest request);

    Uni<GetStageDetailsDeveloperResponse> execute(GetStageDetailsDeveloperRequest request);

    Uni<DeleteStageDeveloperResponse> execute(DeleteStageDeveloperRequest request);

    /*
    Tenant version
     */

    Uni<CreateVersionDeveloperResponse> execute(CreateVersionDeveloperRequest request);

    Uni<GetVersionDetailsDeveloperResponse> execute(GetVersionDetailsDeveloperRequest request);

    Uni<DeleteVersionDeveloperResponse> execute(DeleteVersionDeveloperRequest request);

    /*
    Tenant image
     */

    Uni<CreateImageDeveloperResponse> execute(CreateImageDeveloperRequest request);

    /*
    Tenant deployment
     */

    Uni<CreateDeploymentDeveloperResponse> execute(CreateDeploymentDeveloperRequest request);

    Uni<GetDeploymentDetailsDeveloperResponse> execute(GetDeploymentDetailsDeveloperRequest request);

    Uni<DeleteDeploymentDeveloperResponse> execute(DeleteDeploymentDeveloperRequest request);
}
