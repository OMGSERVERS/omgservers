package com.omgservers.service.entrypoint.support.impl.service.webService.impl.supportApi;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.service.entrypoint.support.impl.service.webService.WebService;
import com.omgservers.service.server.operation.handleApiRequest.HandleApiRequestOperation;
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
@RolesAllowed({UserRoleEnum.Names.SUPPORT})
public class SupportApiImpl implements SupportApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @PermitAll
    public Uni<CreateTokenSupportResponse> createToken(@NotNull final CreateTokenSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createToken);
    }

    @Override
    public Uni<CreateTenantSupportResponse> createTenant(@NotNull final CreateTenantSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenant);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(@NotNull final DeleteTenantSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenant);
    }

    @Override
    public Uni<CreateProjectSupportResponse> createProject(@NotNull final CreateProjectSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createProject);
    }

    @Override
    public Uni<DeleteProjectSupportResponse> deleteProject(@NotNull final DeleteProjectSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteProject);
    }

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(@NotNull final CreateDeveloperSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createDeveloper);
    }

    @Override
    public Uni<DeleteDeveloperSupportResponse> deleteDeveloper(@NotNull final DeleteDeveloperSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteDeveloper);
    }

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            @NotNull final CreateTenantPermissionsSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createTenantPermissions);
    }

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            @NotNull final DeleteTenantPermissionsSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenantPermissions);
    }

    @Override
    public Uni<CreateProjectPermissionsSupportResponse> createProjectPermissions(
            @NotNull final CreateProjectPermissionsSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createProjectPermissions);
    }

    @Override
    public Uni<DeleteProjectPermissionsSupportResponse> deleteProjectPermissions(
            @NotNull final DeleteProjectPermissionsSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteProjectPermissions);
    }

    @Override
    public Uni<CreateStagePermissionsSupportResponse> createStagePermissions(
            @NotNull final CreateStagePermissionsSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::createStagePermissions);
    }

    @Override
    public Uni<DeleteStagePermissionsSupportResponse> deleteStagePermissions(
            @NotNull final DeleteStagePermissionsSupportRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteStagePermissions);
    }
}
