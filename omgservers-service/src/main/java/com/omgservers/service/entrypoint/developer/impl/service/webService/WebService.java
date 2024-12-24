package com.omgservers.service.entrypoint.developer.impl.service.webService;

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
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDashboardDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<CreateTokenDeveloperResponse> execute(CreateTokenDeveloperRequest request);

    /*
    Tenant
     */

    Uni<GetTenantDashboardDeveloperResponse> execute(GetTenantDashboardDeveloperRequest request);

    /*
    Tenant project
     */

    Uni<CreateTenantProjectDeveloperResponse> execute(CreateTenantProjectDeveloperRequest request);

    Uni<CreateTenantProjectAliasDeveloperResponse> execute(CreateTenantProjectAliasDeveloperRequest request);

    Uni<GetTenantProjectDashboardDeveloperResponse> execute(GetTenantProjectDashboardDeveloperRequest request);

    Uni<DeleteTenantProjectDeveloperResponse> execute(DeleteTenantProjectDeveloperRequest request);

    /*
    Tenant stage
     */

    Uni<CreateTenantStageDeveloperResponse> execute(CreateTenantStageDeveloperRequest request);

    Uni<CreateTenantStageAliasDeveloperResponse> execute(CreateTenantStageAliasDeveloperRequest request);

    Uni<GetTenantStageDashboardDeveloperResponse> execute(GetTenantStageDashboardDeveloperRequest request);

    Uni<DeleteTenantStageDeveloperResponse> execute(DeleteTenantStageDeveloperRequest request);

    /*
    Tenant version
     */

    Uni<CreateTenantVersionDeveloperResponse> execute(CreateTenantVersionDeveloperRequest request);

    Uni<UploadFilesArchiveDeveloperResponse> execute(UploadFilesArchiveDeveloperRequest request);

    Uni<GetTenantVersionDashboardDeveloperResponse> execute(GetTenantVersionDashboardDeveloperRequest request);

    Uni<DeleteTenantVersionDeveloperResponse> execute(DeleteTenantVersionDeveloperRequest request);

    /*
    Tenant deployment
     */

    Uni<DeployTenantVersionDeveloperResponse> execute(DeployTenantVersionDeveloperRequest request);

    Uni<GetTenantDeploymentDashboardDeveloperResponse> execute(GetTenantDeploymentDashboardDeveloperRequest request);

    Uni<DeleteTenantDeploymentDeveloperResponse> execute(DeleteTenantDeploymentDeveloperRequest request);

    Uni<CreateLobbyRequestDeveloperResponse> execute(CreateLobbyRequestDeveloperRequest request);

    Uni<DeleteLobbyDeveloperResponse> execute(DeleteLobbyDeveloperRequest request);

    Uni<CreateMatchmakerRequestDeveloperResponse> execute(CreateMatchmakerRequestDeveloperRequest request);

    Uni<DeleteMatchmakerDeveloperResponse> execute(DeleteMatchmakerDeveloperRequest request);
}
