package com.omgservers.service.entrypoint.developer.impl.service.webService;

import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.CreateTenantProjectDeveloperResponse;
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

    Uni<CreateTokenDeveloperResponse> createToken(CreateTokenDeveloperRequest request);

    Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(GetTenantDashboardDeveloperRequest request);

    Uni<CreateTenantProjectDeveloperResponse> createTenantProject(CreateTenantProjectDeveloperRequest request);

    Uni<GetTenantProjectDashboardDeveloperResponse> getTenantProjectDashboard(
            GetTenantProjectDashboardDeveloperRequest request);

    Uni<DeleteTenantProjectDeveloperResponse> deleteTenantProject(DeleteTenantProjectDeveloperRequest request);

    Uni<CreateTenantStageDeveloperResponse> createTenantStage(CreateTenantStageDeveloperRequest request);

    Uni<GetTenantStageDashboardDeveloperResponse> getTenantStageDashboard(
            GetTenantStageDashboardDeveloperRequest request);

    Uni<DeleteTenantStageDeveloperResponse> deleteTenantStage(DeleteTenantStageDeveloperRequest request);

    Uni<CreateTenantVersionDeveloperResponse> createTenantVersion(CreateTenantVersionDeveloperRequest request);

    Uni<UploadFilesArchiveDeveloperResponse> uploadFilesArchive(UploadFilesArchiveDeveloperRequest request);

    Uni<GetTenantVersionDashboardDeveloperResponse> getTenantVersionDashboard(
            GetTenantVersionDashboardDeveloperRequest request);

    Uni<DeleteTenantVersionDeveloperResponse> deleteTenantVersion(DeleteTenantVersionDeveloperRequest request);

    Uni<DeployTenantVersionDeveloperResponse> deployTenantVersion(DeployTenantVersionDeveloperRequest request);

    Uni<GetTenantDeploymentDashboardDeveloperResponse> getTenantDeploymentDashboard(
            GetTenantDeploymentDashboardDeveloperRequest request);

    Uni<DeleteTenantDeploymentDeveloperResponse> deleteTenantDeployment(DeleteTenantDeploymentDeveloperRequest request);

    Uni<DeleteLobbyDeveloperResponse> deleteLobby(DeleteLobbyDeveloperRequest request);

    Uni<DeleteMatchmakerDeveloperResponse> deleteMatchmaker(DeleteMatchmakerDeveloperRequest request);
}
