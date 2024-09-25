package com.omgservers.service.entrypoint.support.impl.service.supportService.impl;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateDeveloperMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantProjectMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantProjectPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantStagePermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteDeveloperMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteProjectPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantProjectMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantStagePermissionsMethod;
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

    final CreateTenantProjectPermissionsMethod createTenantProjectPermissionsMethod;
    final CreateTenantStagePermissionsMethod createTenantStagePermissionsMethod;
    final DeleteTenantStagePermissionsMethod deleteTenantStagePermissionsMethod;
    final DeleteProjectPermissionsMethod deleteProjectPermissionsMethod;
    final CreateTenantPermissionsMethod createTenantPermissionsMethod;
    final DeleteTenantPermissionsMethod deleteTenantPermissionsMethod;
    final CreateTenantProjectMethod createTenantProjectMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final CreateDeveloperMethod createDeveloperMethod;
    final DeleteDeveloperMethod deleteDeveloperMethod;
    final CreateTenantMethod createTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenSupportResponse> createToken(@Valid final CreateTokenSupportRequest request) {
        return createTokenMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantSupportResponse> createTenant(@Valid final CreateTenantSupportRequest request) {
        return createTenantMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(@Valid final DeleteTenantSupportRequest request) {
        return deleteTenantMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectSupportResponse> createTenantProject(
            @Valid final CreateTenantProjectSupportRequest request) {
        return createTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectSupportResponse> deleteTenantProject(
            @Valid final DeleteTenantProjectSupportRequest request) {
        return deleteTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(@Valid final CreateDeveloperSupportRequest request) {
        return createDeveloperMethod.execute(request);
    }

    @Override
    public Uni<DeleteDeveloperSupportResponse> deleteDeveloper(@Valid final DeleteDeveloperSupportRequest request) {
        return deleteDeveloperMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            @Valid final CreateTenantPermissionsSupportRequest request) {
        return createTenantPermissionsMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            @Valid final DeleteTenantPermissionsSupportRequest request) {
        return deleteTenantPermissionsMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectPermissionsSupportResponse> createTenantProjectPermissions(
            @Valid final CreateTenantProjectPermissionsSupportRequest request) {
        return createTenantProjectPermissionsMethod.execute(request);
    }

    @Override
    public Uni<DeleteProjectPermissionsSupportResponse> deleteTenantProjectPermissions(
            @Valid final DeleteProjectPermissionsSupportRequest request) {
        return deleteProjectPermissionsMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantStagePermissionsSupportResponse> createTenantStagePermissions(
            @Valid final CreateTenantStagePermissionsSupportRequest request) {
        return createTenantStagePermissionsMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantStagePermissionsSupportResponse> deleteTenantStagePermissions(
            @Valid final DeleteTenantStagePermissionsSupportRequest request) {
        return deleteTenantStagePermissionsMethod.execute(request);
    }
}
