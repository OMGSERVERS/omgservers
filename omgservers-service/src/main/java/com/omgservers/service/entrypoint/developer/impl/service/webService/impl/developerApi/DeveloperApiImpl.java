package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

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
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.developer.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.DEVELOPER})
class DeveloperApiImpl implements DeveloperApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenDeveloperResponse> createToken(@NotNull final CreateTokenDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> getTenantDashboard(
            @NotNull final GetTenantDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantDashboard);
    }

    @Override
    public Uni<CreateTenantProjectDeveloperResponse> createTenantProject(
            @NotNull final CreateTenantProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenantProject);
    }

    @Override
    public Uni<GetTenantProjectDashboardDeveloperResponse> getTenantProjectDashboard(
            @NotNull final GetTenantProjectDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantProjectDashboard);
    }

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> deleteTenantProject(
            @NotNull final DeleteTenantProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantProject);
    }

    @Override
    public Uni<CreateTenantStageDeveloperResponse> createTenantStage(
            @NotNull final CreateTenantStageDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenantStage);
    }

    @Override
    public Uni<GetTenantStageDashboardDeveloperResponse> getTenantStageDashboard(
            @NotNull final GetTenantStageDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantStageDashboard);
    }

    @Override
    public Uni<DeleteTenantStageDeveloperResponse> deleteTenantStage(
            @NotNull final DeleteTenantStageDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantStage);
    }

    @Override
    public Uni<CreateTenantVersionDeveloperResponse> createTenantVersion(
            @NotNull final CreateTenantVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenantVersion);
    }

    @Override
    public Uni<UploadFilesArchiveDeveloperResponse> uploadFilesArchive(final Long tenantId,
                                                                       final Long tenantVersionId,
                                                                       final List<FileUpload> files) {
        final var request = new UploadFilesArchiveDeveloperRequest(tenantId, tenantVersionId, files);
        return handleApiRequestOperation.handleApiRequest(log, request, webService::uploadFilesArchive);
    }

    @Override
    public Uni<GetTenantVersionDashboardDeveloperResponse> getTenantVersionDashboard(
            final GetTenantVersionDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantVersionDashboard);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> deleteTenantVersion(
            @NotNull final DeleteTenantVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantVersion);
    }


    @Override
    public Uni<DeployTenantVersionDeveloperResponse> deployTenantVersion(
            @NotNull final DeployTenantVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deployTenantVersion);
    }

    @Override
    public Uni<GetTenantDeploymentDashboardDeveloperResponse> getTenantDeploymentDashboard(
            @NotNull final GetTenantDeploymentDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenantDeploymentDashboard);
    }

    @Override
    public Uni<DeleteTenantDeploymentDeveloperResponse> deleteTenantDeployment(
            @NotNull final DeleteTenantDeploymentDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantDeployment);
    }

    @Override
    public Uni<DeleteLobbyDeveloperResponse> deleteLobby(@NotNull final DeleteLobbyDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteLobby);
    }

    @Override
    public Uni<DeleteMatchmakerDeveloperResponse> deleteMatchmaker(
            @NotNull final DeleteMatchmakerDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteMatchmaker);
    }
}
