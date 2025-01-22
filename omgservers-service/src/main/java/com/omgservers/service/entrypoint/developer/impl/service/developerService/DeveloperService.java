package com.omgservers.service.entrypoint.developer.impl.service.developerService;

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
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantDeploymentDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantProjectDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.GetTenantStageDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperResponse;
import com.omgservers.schema.entrypoint.developer.GetTenantVersionDetailsDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperRequest;
import com.omgservers.schema.entrypoint.developer.UploadFilesArchiveDeveloperResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DeveloperService {

    Uni<CreateTokenDeveloperResponse> execute(@Valid CreateTokenDeveloperRequest request);

    Uni<GetTenantDetailsDeveloperResponse> execute(@Valid GetTenantDetailsDeveloperRequest request);

    Uni<CreateTenantProjectDeveloperResponse> execute(@Valid CreateTenantProjectDeveloperRequest request);

    Uni<CreateTenantProjectAliasDeveloperResponse> execute(@Valid CreateTenantProjectAliasDeveloperRequest request);

    Uni<GetTenantProjectDetailsDeveloperResponse> execute(@Valid GetTenantProjectDetailsDeveloperRequest request);

    Uni<DeleteTenantProjectDeveloperResponse> execute(@Valid DeleteTenantProjectDeveloperRequest request);

    Uni<CreateTenantStageDeveloperResponse> execute(@Valid CreateTenantStageDeveloperRequest request);

    Uni<CreateTenantStageAliasDeveloperResponse> execute(@Valid CreateTenantStageAliasDeveloperRequest request);

    Uni<GetTenantStageDetailsDeveloperResponse> execute(@Valid GetTenantStageDetailsDeveloperRequest request);

    Uni<DeleteTenantStageDeveloperResponse> execute(@Valid DeleteTenantStageDeveloperRequest request);

    Uni<CreateTenantVersionDeveloperResponse> execute(@Valid CreateTenantVersionDeveloperRequest request);

    Uni<UploadFilesArchiveDeveloperResponse> execute(@Valid UploadFilesArchiveDeveloperRequest request);

    Uni<GetTenantVersionDetailsDeveloperResponse> execute(@Valid GetTenantVersionDetailsDeveloperRequest request);

    Uni<DeleteTenantVersionDeveloperResponse> execute(@Valid DeleteTenantVersionDeveloperRequest request);

    Uni<DeployTenantVersionDeveloperResponse> execute(@Valid DeployTenantVersionDeveloperRequest request);

    Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(
            @Valid GetTenantDeploymentDetailsDeveloperRequest request);

    Uni<DeleteTenantDeploymentDeveloperResponse> execute(@Valid DeleteTenantDeploymentDeveloperRequest request);

    Uni<CreateLobbyRequestDeveloperResponse> execute(@Valid CreateLobbyRequestDeveloperRequest request);

    Uni<DeleteLobbyDeveloperResponse> execute(@Valid DeleteLobbyDeveloperRequest request);

    Uni<CreateMatchmakerRequestDeveloperResponse> execute(@Valid CreateMatchmakerRequestDeveloperRequest request);

    Uni<DeleteMatchmakerDeveloperResponse> execute(@Valid DeleteMatchmakerDeveloperRequest request);
}
