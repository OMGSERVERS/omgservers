package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

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
    public Uni<CreateTokenDeveloperResponse> execute(@NotNull final CreateTokenDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant
     */

    @Override
    public Uni<GetTenantDashboardDeveloperResponse> execute(
            @NotNull final GetTenantDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant project
     */

    @Override
    public Uni<CreateTenantProjectDeveloperResponse> execute(
            @NotNull final CreateTenantProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CreateTenantProjectAliasDeveloperResponse> execute(
            @NotNull final CreateTenantProjectAliasDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantProjectDashboardDeveloperResponse> execute(
            @NotNull final GetTenantProjectDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantProjectDeveloperResponse> execute(
            @NotNull final DeleteTenantProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant stage
     */

    @Override
    public Uni<CreateTenantStageDeveloperResponse> execute(
            @NotNull final CreateTenantStageDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CreateTenantStageAliasDeveloperResponse> execute(
            @NotNull final CreateTenantStageAliasDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantStageDashboardDeveloperResponse> execute(
            @NotNull final GetTenantStageDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantStageDeveloperResponse> execute(
            @NotNull final DeleteTenantStageDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant version
     */

    @Override
    public Uni<CreateTenantVersionDeveloperResponse> execute(
            @NotNull final CreateTenantVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<UploadFilesArchiveDeveloperResponse> execute(final String tenant,
                                                            final Long tenantVersionId,
                                                            final List<FileUpload> files) {
        final var request = new UploadFilesArchiveDeveloperRequest(tenant, tenantVersionId, files);
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantVersionDashboardDeveloperResponse> execute(
            final GetTenantVersionDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> execute(
            @NotNull final DeleteTenantVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant deployment
     */

    @Override
    public Uni<DeployTenantVersionDeveloperResponse> execute(
            @NotNull final DeployTenantVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetTenantDeploymentDashboardDeveloperResponse> execute(
            @NotNull final GetTenantDeploymentDashboardDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantDeploymentDeveloperResponse> execute(
            @NotNull final DeleteTenantDeploymentDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CreateLobbyRequestDeveloperResponse> execute(
            @NotNull final CreateLobbyRequestDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteLobbyDeveloperResponse> execute(@NotNull final DeleteLobbyDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CreateMatchmakerRequestDeveloperResponse> execute(
            @NotNull final CreateMatchmakerRequestDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteMatchmakerDeveloperResponse> execute(
            @NotNull final DeleteMatchmakerDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
