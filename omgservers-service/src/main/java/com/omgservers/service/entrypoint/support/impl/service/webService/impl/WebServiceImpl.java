package com.omgservers.service.entrypoint.support.impl.service.webService.impl;

import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantProjectAliasSupportResponse;
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
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportResponse;
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
    public Uni<CreateTokenSupportResponse> execute(final CreateTokenSupportRequest request) {
        return supportService.execute(request);
    }

    /*
    Tenant
     */

    @Override
    public Uni<CreateTenantSupportResponse> execute(final CreateTenantSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<CreateTenantAliasSupportResponse> execute(final CreateTenantAliasSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> execute(final DeleteTenantSupportRequest request) {
        return supportService.execute(request);
    }

    /*
    Project
     */

    @Override
    public Uni<CreateTenantProjectSupportResponse> execute(final CreateTenantProjectSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectAliasSupportResponse> execute(final CreateTenantProjectAliasSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectSupportResponse> execute(final DeleteTenantProjectSupportRequest request) {
        return supportService.execute(request);
    }

    /*
    Stage
     */

    @Override
    public Uni<CreateTenantStageSupportResponse> execute(final CreateTenantStageSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<CreateTenantStageAliasSupportResponse> execute(final CreateTenantStageAliasSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<DeleteTenantStageSupportResponse> execute(final DeleteTenantStageSupportRequest request) {
        return supportService.execute(request);
    }

    /*
    Developer
     */

    @Override
    public Uni<CreateDeveloperSupportResponse> execute(final CreateDeveloperSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<CreateDeveloperAliasSupportResponse> execute(final CreateDeveloperAliasSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<DeleteDeveloperSupportResponse> execute(DeleteDeveloperSupportRequest request) {
        return supportService.execute(request);
    }

    /*
    Permissions
     */

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> execute(
            final CreateTenantPermissionsSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> execute(
            final DeleteTenantPermissionsSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectPermissionsSupportResponse> execute(
            CreateTenantProjectPermissionsSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectPermissionsSupportResponse> execute(
            DeleteTenantProjectPermissionsSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<CreateTenantStagePermissionsSupportResponse> execute(
            CreateTenantStagePermissionsSupportRequest request) {
        return supportService.execute(request);
    }

    @Override
    public Uni<DeleteTenantStagePermissionsSupportResponse> execute(
            DeleteTenantStagePermissionsSupportRequest request) {
        return supportService.execute(request);
    }
}
