package com.omgservers.service.entrypoint.developer.impl.service.webService.impl.developerApi;

import com.omgservers.schema.entrypoint.developer.*;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.developer.impl.service.webService.WebService;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
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
    public Uni<GetTenantDetailsDeveloperResponse> execute(
            @NotNull final GetTenantDetailsDeveloperRequest request) {
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
    public Uni<GetTenantProjectDetailsDeveloperResponse> execute(
            @NotNull final GetTenantProjectDetailsDeveloperRequest request) {
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
    public Uni<GetTenantStageDetailsDeveloperResponse> execute(
            @NotNull final GetTenantStageDetailsDeveloperRequest request) {
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
    public Uni<GetTenantVersionDetailsDeveloperResponse> execute(
            final GetTenantVersionDetailsDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteTenantVersionDeveloperResponse> execute(
            @NotNull final DeleteTenantVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant image
     */

    @Override
    public Uni<CreateTenantImageDeveloperResponse> execute(@NotNull final CreateTenantImageDeveloperRequest request) {
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
    public Uni<GetTenantDeploymentDetailsDeveloperResponse> execute(
            @NotNull final GetTenantDeploymentDetailsDeveloperRequest request) {
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
