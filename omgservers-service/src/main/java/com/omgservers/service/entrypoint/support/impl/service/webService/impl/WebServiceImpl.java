package com.omgservers.service.entrypoint.support.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
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
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;
import com.omgservers.service.entrypoint.support.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class WebServiceImpl implements WebService {

    final SupportService supportService;

    @Override
    public Uni<CreateTokenSupportResponse> createToken(final CreateTokenSupportRequest request) {
        return supportService.createToken(request);
    }

    @Override
    public Uni<CreateTenantSupportResponse> createTenant(final CreateTenantSupportRequest request) {
        return supportService.createTenant(request);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> deleteTenant(final DeleteTenantSupportRequest request) {
        return supportService.deleteTenant(request);
    }

    @Override
    public Uni<CreateTenantProjectSupportResponse> createTenantProject(final CreateTenantProjectSupportRequest request) {
        return supportService.createTenantProject(request);
    }

    @Override
    public Uni<DeleteTenantProjectSupportResponse> deleteTenantProject(final DeleteTenantProjectSupportRequest request) {
        return supportService.deleteTenantProject(request);
    }

    @Override
    public Uni<CreateDeveloperSupportResponse> createDeveloper(final CreateDeveloperSupportRequest request) {
        return supportService.createDeveloper(request);
    }

    @Override
    public Uni<DeleteDeveloperSupportResponse> deleteDeveloper(DeleteDeveloperSupportRequest request) {
        return supportService.deleteDeveloper(request);
    }

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> createTenantPermissions(
            final CreateTenantPermissionsSupportRequest request) {
        return supportService.createTenantPermissions(request);
    }

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> deleteTenantPermissions(
            final DeleteTenantPermissionsSupportRequest request) {
        return supportService.deleteTenantPermissions(request);
    }

    @Override
    public Uni<CreateTenantProjectPermissionsSupportResponse> createTenantProjectPermissions(
            CreateTenantProjectPermissionsSupportRequest request) {
        return supportService.createTenantProjectPermissions(request);
    }

    @Override
    public Uni<DeleteProjectPermissionsSupportResponse> deleteTenantProjectPermissions(
            DeleteProjectPermissionsSupportRequest request) {
        return supportService.deleteTenantProjectPermissions(request);
    }

    @Override
    public Uni<CreateTenantStagePermissionsSupportResponse> createTenantStagePermissions(
            CreateTenantStagePermissionsSupportRequest request) {
        return supportService.createTenantStagePermissions(request);
    }

    @Override
    public Uni<DeleteTenantStagePermissionsSupportResponse> deleteTenantStagePermissions(
            DeleteTenantStagePermissionsSupportRequest request) {
        return supportService.deleteTenantStagePermissions(request);
    }
}
