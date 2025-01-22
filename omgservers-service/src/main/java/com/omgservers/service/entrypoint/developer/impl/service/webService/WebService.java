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
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
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
