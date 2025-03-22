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

    Uni<CreateTenantProjectDeveloperResponse> execute(CreateTenantProjectDeveloperRequest request);

    Uni<CreateTenantProjectAliasDeveloperResponse> execute(CreateTenantProjectAliasDeveloperRequest request);

    Uni<GetTenantProjectDetailsDeveloperResponse> execute(GetTenantProjectDetailsDeveloperRequest request);

    Uni<DeleteTenantProjectDeveloperResponse> execute(DeleteTenantProjectDeveloperRequest request);

    /*
    Tenant stage
     */

    Uni<CreateTenantStageDeveloperResponse> execute(CreateTenantStageDeveloperRequest request);

    Uni<CreateTenantStageAliasDeveloperResponse> execute(CreateTenantStageAliasDeveloperRequest request);

    Uni<GetTenantStageDetailsDeveloperResponse> execute(GetTenantStageDetailsDeveloperRequest request);

    Uni<DeleteTenantStageDeveloperResponse> execute(DeleteTenantStageDeveloperRequest request);

    /*
    Tenant version
     */

    Uni<CreateTenantVersionDeveloperResponse> execute(CreateTenantVersionDeveloperRequest request);

    Uni<UploadFilesArchiveDeveloperResponse> execute(UploadFilesArchiveDeveloperRequest request);

    Uni<GetTenantVersionDetailsDeveloperResponse> execute(GetTenantVersionDetailsDeveloperRequest request);

    Uni<DeleteTenantVersionDeveloperResponse> execute(DeleteTenantVersionDeveloperRequest request);

    /*
    Tenant image
     */

    Uni<CreateTenantImageDeveloperResponse> execute(CreateTenantImageDeveloperRequest request);

    /*
    Tenant deployment
     */

    Uni<DeployTenantVersionDeveloperResponse> execute(DeployTenantVersionDeveloperRequest request);

    Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(GetTenantDeploymentDetailsDeveloperRequest request);

    Uni<DeleteTenantDeploymentDeveloperResponse> execute(DeleteTenantDeploymentDeveloperRequest request);

    Uni<CreateLobbyRequestDeveloperResponse> execute(CreateLobbyRequestDeveloperRequest request);

    Uni<DeleteLobbyDeveloperResponse> execute(DeleteLobbyDeveloperRequest request);

    Uni<CreateMatchmakerRequestDeveloperResponse> execute(CreateMatchmakerRequestDeveloperRequest request);

    Uni<DeleteMatchmakerDeveloperResponse> execute(DeleteMatchmakerDeveloperRequest request);
}
