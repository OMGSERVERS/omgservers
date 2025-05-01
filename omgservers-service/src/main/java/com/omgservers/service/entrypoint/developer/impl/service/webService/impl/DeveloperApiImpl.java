package com.omgservers.service.entrypoint.developer.impl.service.webService.impl;

import com.omgservers.api.DeveloperApi;
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
    public Uni<CreateProjectDeveloperResponse> execute(
            @NotNull final CreateProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CreateProjectAliasDeveloperResponse> execute(
            @NotNull final CreateProjectAliasDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetProjectDetailsDeveloperResponse> execute(
            @NotNull final GetProjectDetailsDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteProjectDeveloperResponse> execute(
            @NotNull final DeleteProjectDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant stage
     */

    @Override
    public Uni<CreateStageDeveloperResponse> execute(
            @NotNull final CreateStageDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<CreateStageAliasDeveloperResponse> execute(
            @NotNull final CreateStageAliasDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetStageDetailsDeveloperResponse> execute(
            @NotNull final GetStageDetailsDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteStageDeveloperResponse> execute(
            @NotNull final DeleteStageDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant version
     */

    @Override
    public Uni<CreateVersionDeveloperResponse> execute(
            @NotNull final CreateVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetVersionDetailsDeveloperResponse> execute(
            final GetVersionDetailsDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteVersionDeveloperResponse> execute(
            @NotNull final DeleteVersionDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant image
     */

    @Override
    public Uni<CreateImageDeveloperResponse> execute(@NotNull final CreateImageDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    Tenant deployment
     */

    @Override
    public Uni<CreateDeploymentDeveloperResponse> execute(@NotNull final CreateDeploymentDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<GetDeploymentDetailsDeveloperResponse> execute(
            @NotNull final GetDeploymentDetailsDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteDeploymentDeveloperResponse> execute(@NotNull final DeleteDeploymentDeveloperRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
