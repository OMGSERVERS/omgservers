package com.omgservers.service.entrypoint.support.impl.service.supportService.impl;

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
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageAliasSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantStageSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTenantSupportResponse;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateTokenSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteDeveloperSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectPermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantProjectSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStagePermissionsSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantStageSupportResponse;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportRequest;
import com.omgservers.schema.entrypoint.support.DeleteTenantSupportResponse;
import com.omgservers.service.entrypoint.support.impl.service.supportService.SupportService;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateDeveloperAliasMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateDeveloperMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantAliasMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantProjectAliasMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantProjectMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantProjectPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantStageAliasMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantStageMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTenantStagePermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.CreateTokenMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteDeveloperMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteProjectPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantPermissionsMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantProjectMethod;
import com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.DeleteTenantStageMethod;
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
    final CreateTenantProjectAliasMethod createTenantProjectAliasMethod;
    final CreateTenantPermissionsMethod createTenantPermissionsMethod;
    final DeleteTenantPermissionsMethod deleteTenantPermissionsMethod;
    final CreateTenantStageAliasMethod createTenantStageAliasMethod;
    final CreateDeveloperAliasMethod createDeveloperAliasMethod;
    final CreateTenantProjectMethod createTenantProjectMethod;
    final DeleteTenantProjectMethod deleteTenantProjectMethod;
    final CreateTenantAliasMethod createTenantAliasMethod;
    final CreateTenantStageMethod createTenantStageMethod;
    final DeleteTenantStageMethod deleteTenantStageMethod;
    final CreateDeveloperMethod createDeveloperMethod;
    final DeleteDeveloperMethod deleteDeveloperMethod;
    final CreateTenantMethod createTenantMethod;
    final DeleteTenantMethod deleteTenantMethod;
    final CreateTokenMethod createTokenMethod;

    @Override
    public Uni<CreateTokenSupportResponse> execute(@Valid final CreateTokenSupportRequest request) {
        return createTokenMethod.execute(request);
    }

    /*
    Tenant
     */

    @Override
    public Uni<CreateTenantSupportResponse> execute(@Valid final CreateTenantSupportRequest request) {
        return createTenantMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantAliasSupportResponse> execute(@Valid final CreateTenantAliasSupportRequest request) {
        return createTenantAliasMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantSupportResponse> execute(@Valid final DeleteTenantSupportRequest request) {
        return deleteTenantMethod.execute(request);
    }

    /*
    Project
     */

    @Override
    public Uni<CreateTenantProjectSupportResponse> execute(@Valid final CreateTenantProjectSupportRequest request) {
        return createTenantProjectMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectAliasSupportResponse> execute(
            @Valid final CreateTenantProjectAliasSupportRequest request) {
        return createTenantProjectAliasMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectSupportResponse> execute(
            @Valid final DeleteTenantProjectSupportRequest request) {
        return deleteTenantProjectMethod.execute(request);
    }

    /*
    Stage
     */

    @Override
    public Uni<CreateTenantStageSupportResponse> execute(@Valid final CreateTenantStageSupportRequest request) {
        return createTenantStageMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantStageAliasSupportResponse> execute(
            @Valid final CreateTenantStageAliasSupportRequest request) {
        return createTenantStageAliasMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantStageSupportResponse> execute(@Valid final DeleteTenantStageSupportRequest request) {
        return deleteTenantStageMethod.execute(request);
    }

    /*
    Developer
     */

    @Override
    public Uni<CreateDeveloperSupportResponse> execute(@Valid final CreateDeveloperSupportRequest request) {
        return createDeveloperMethod.execute(request);
    }

    @Override
    public Uni<CreateDeveloperAliasSupportResponse> execute(@Valid final CreateDeveloperAliasSupportRequest request) {
        return createDeveloperAliasMethod.execute(request);
    }

    @Override
    public Uni<DeleteDeveloperSupportResponse> execute(@Valid final DeleteDeveloperSupportRequest request) {
        return deleteDeveloperMethod.execute(request);
    }

    /*
    Permissions
     */

    @Override
    public Uni<CreateTenantPermissionsSupportResponse> execute(
            @Valid final CreateTenantPermissionsSupportRequest request) {
        return createTenantPermissionsMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantPermissionsSupportResponse> execute(
            @Valid final DeleteTenantPermissionsSupportRequest request) {
        return deleteTenantPermissionsMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantProjectPermissionsSupportResponse> execute(
            @Valid final CreateTenantProjectPermissionsSupportRequest request) {
        return createTenantProjectPermissionsMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantProjectPermissionsSupportResponse> execute(
            @Valid final DeleteTenantProjectPermissionsSupportRequest request) {
        return deleteProjectPermissionsMethod.execute(request);
    }

    @Override
    public Uni<CreateTenantStagePermissionsSupportResponse> execute(
            @Valid final CreateTenantStagePermissionsSupportRequest request) {
        return createTenantStagePermissionsMethod.execute(request);
    }

    @Override
    public Uni<DeleteTenantStagePermissionsSupportResponse> execute(
            @Valid final DeleteTenantStagePermissionsSupportRequest request) {
        return deleteTenantStagePermissionsMethod.execute(request);
    }
}
