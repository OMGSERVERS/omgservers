package com.omgservers.service.entrypoint.support.impl.service.supportService.impl;

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
import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createDeveloper.CreateDeveloperMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProject.CreateProjectMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProjectPermissions.CreateProjectPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createStagePermissions.CreateStagePermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createTenant.CreateTenantMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createTenantPermissions.CreateTenantPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createToken.CreateTokenMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteDeveloper.DeleteDeveloperMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteProject.DeleteProjectMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteProjectPermissions.DeleteProjectPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteStagePermissions.DeleteStagePermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteTenant.DeleteTenantMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.deleteTenantPermissions.DeleteTenantPermissionsMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SupportServiceImpl implements SupportService {

    final CreateProjectPermissionsMethod createProjectPermissionsMethod;
    final DeleteProjectPermissionsMethod deleteProjectPermissionsMethod;
    final CreateTenantPermissionsMethod createTenantPermissionsMethod;
    final DeleteTenantPermissionsMethod deleteTenantPermissionsMethod;
    final CreateStagePermissionsMethod createStagePermissionsMethod;
    final DeleteStagePermissionsMethod deleteStagePermissionsMethod;
    final CreateDeveloperMethod createDeveloperMethod;
    final DeleteDeveloperMethod deleteDeveloperMethod;
    final CreateProjectMethod createProjectMethod;
    final DeleteProjectMethod deleteProjectMethod;
    final CreateTenantMethod createTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenSupportResponse> createToken(@Valid final CreateTokenSupportRequest request) {
        return createTokenMethod.createToken(request);
    }

    @Override
    public Uni<CreateTenantSupportResponse> createTenant(@Valid final CreateTenantSupportRequest request) {
        return createTenantMethod.createTenant(request);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(@Valid final DeleteTenantSupportRequest request) {
        return deleteTenantMethod.deleteTenant(request);
    }

    @Override
    public Uni<CreateProjectSupportResponse> createProject(@Valid final CreateProjectSupportRequest request) {
        return createProjectMethod.createProject(request);
    }

    @Override
    public Uni<DeleteProjectSupportResponse> deleteProject(@Valid final DeleteProjectSupportRequest request) {
        return deleteProjectMethod.deleteProject(request);
    }

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(@Valid final CreateDeveloperSupportRequest request) {
        return createDeveloperMethod.createDeveloper(request);
    }

    @Override
    public Uni<DeleteDeveloperSupportResponse> deleteDeveloper(@Valid final DeleteDeveloperSupportRequest request) {
        return deleteDeveloperMethod.deleteDeveloper(request);
    }

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            @Valid final CreateTenantPermissionsSupportRequest request) {
        return createTenantPermissionsMethod.createTenantPermissions(request);
    }

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            @Valid final DeleteTenantPermissionsSupportRequest request) {
        return deleteTenantPermissionsMethod.deleteTenantPermissions(request);
    }

    @Override
    public Uni<CreateProjectPermissionsSupportResponse> createProjectPermissions(
            @Valid final CreateProjectPermissionsSupportRequest request) {
        return createProjectPermissionsMethod.createProjectPermissions(request);
    }

    @Override
    public Uni<DeleteProjectPermissionsSupportResponse> deleteProjectPermissions(
            @Valid final DeleteProjectPermissionsSupportRequest request) {
        return deleteProjectPermissionsMethod.deleteProjectPermissions(request);
    }

    @Override
    public Uni<CreateStagePermissionsSupportResponse> createStagePermissions(
            @Valid final CreateStagePermissionsSupportRequest request) {
        return createStagePermissionsMethod.createStagePermissions(request);
    }

    @Override
    public Uni<DeleteStagePermissionsSupportResponse> deleteStagePermissions(
            @Valid final DeleteStagePermissionsSupportRequest request) {
        return deleteStagePermissionsMethod.deleteStagePermissions(request);
    }
}
