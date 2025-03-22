package com.omgservers.service.entrypoint.developer.impl.service.developerService;

import com.omgservers.schema.entrypoint.developer.*;
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

    Uni<CreateTenantImageDeveloperResponse> execute(@Valid CreateTenantImageDeveloperRequest request);

    Uni<DeployTenantVersionDeveloperResponse> execute(@Valid DeployTenantVersionDeveloperRequest request);

    Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(
            @Valid GetTenantDeploymentDetailsDeveloperRequest request);

    Uni<DeleteTenantDeploymentDeveloperResponse> execute(@Valid DeleteTenantDeploymentDeveloperRequest request);

    Uni<CreateLobbyRequestDeveloperResponse> execute(@Valid CreateLobbyRequestDeveloperRequest request);

    Uni<DeleteLobbyDeveloperResponse> execute(@Valid DeleteLobbyDeveloperRequest request);

    Uni<CreateMatchmakerRequestDeveloperResponse> execute(@Valid CreateMatchmakerRequestDeveloperRequest request);

    Uni<DeleteMatchmakerDeveloperResponse> execute(@Valid DeleteMatchmakerDeveloperRequest request);
}
