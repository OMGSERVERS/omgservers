package com.omgservers.service.module.tenant.impl.service.versionService.impl;

import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.model.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.service.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.service.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionMatchmaker.DeleteVersionMatchmakerMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.deleteVersionRuntime.DeleteVersionRuntimeMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findStageVersionId.FindStageVersionIdMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionMatchmaker.FindVersionMatchmakerMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.findVersionRuntime.FindVersionRuntimeMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionConfig.GetVersionConfigMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionMatchmaker.GetVersionMatchmakerMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionRuntime.GetVersionRuntimeMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.selectVersionMatchmaker.SelectVersionMatchmakerMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.selectVersionRuntime.SelectVersionRuntimeMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersion.SyncVersionMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmaker.SyncVersionMatchmakerMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.syncVersionRuntime.SyncVersionRuntimeMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakers.ViewVersionMatchmakersMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionRuntimes.ViewVersionRuntimesMethod;
import com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersions.ViewVersionsMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionServiceImpl implements VersionService {

    final SelectVersionMatchmakerMethod selectVersionMatchmakerMethod;
    final DeleteVersionMatchmakerMethod deleteVersionMatchmakerMethod;
    final ViewVersionMatchmakersMethod viewVersionMatchmakersMethod;
    final SyncVersionMatchmakerMethod syncVersionMatchmakerMethod;
    final FindVersionMatchmakerMethod findVersionMatchmakerMethod;
    final SelectVersionRuntimeMethod selectVersionRuntimeMethod;
    final DeleteVersionRuntimeMethod deleteVersionRuntimeMethod;
    final ViewVersionRuntimesMethod viewVersionRuntimesMethod;
    final SyncVersionRuntimeMethod syncVersionRuntimeMethod;
    final FindVersionRuntimeMethod findVersionRuntimeMethod;
    final FindStageVersionIdMethod findStageVersionIdMethod;
    final GetVersionMatchmakerMethod getVersionMatchmaker;
    final GetVersionRuntimeMethod getVersionRuntimeMethod;
    final GetVersionConfigMethod getVersionConfigMethod;
    final DeleteVersionMethod deleteVersionMethod;
    final ViewVersionsMethod viewVersionsMethod;
    final SyncVersionMethod syncVersionMethod;
    final GetVersionMethod getVersionMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetVersionResponse> getVersion(@Valid final GetVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersion,
                getVersionMethod::getVersion);
    }

    @Override
    public Uni<SyncVersionResponse> syncVersion(@Valid final SyncVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersion,
                syncVersionMethod::syncVersion);
    }

    @Override
    public Uni<ViewVersionsResponse> viewVersions(@Valid final ViewVersionsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersions,
                viewVersionsMethod::viewVersions);
    }

    @Override
    public Uni<DeleteVersionResponse> deleteVersion(@Valid final DeleteVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetVersionConfigResponse> getVersionConfig(@Valid final GetVersionConfigRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionConfig,
                getVersionConfigMethod::getVersionConfig);
    }

    @Override
    public Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(@Valid final GetVersionMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionMatchmaker,
                getVersionMatchmaker::getVersionMatchmaker);
    }

    @Override
    public Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(@Valid final SyncVersionMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionMatchmaker,
                syncVersionMatchmakerMethod::syncVersionMatchmaker);
    }

    @Override
    public Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(@Valid final FindVersionMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionMatchmaker,
                findVersionMatchmakerMethod::findVersionMatchmaker);
    }

    @Override
    public Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(
            @Valid final SelectVersionMatchmakerRequest request) {
        return selectVersionMatchmakerMethod.selectVersionMatchmaker(request);
    }

    @Override
    public Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(
            @Valid final ViewVersionMatchmakersRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionMatchmakers,
                viewVersionMatchmakersMethod::viewVersionMatchmakers);
    }

    @Override
    public Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(
            @Valid final DeleteVersionMatchmakerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionMatchmaker,
                deleteVersionMatchmakerMethod::deleteVersionMatchmaker);
    }

    @Override
    public Uni<GetVersionRuntimeResponse> getVersionRuntime(@Valid final GetVersionRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionRuntime,
                getVersionRuntimeMethod::getVersionRuntime);
    }

    @Override
    public Uni<SyncVersionRuntimeResponse> syncVersionRuntime(@Valid final SyncVersionRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersionRuntime,
                syncVersionRuntimeMethod::syncVersionRuntime);
    }

    @Override
    public Uni<FindVersionRuntimeResponse> findVersionRuntime(@Valid final FindVersionRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findVersionRuntime,
                findVersionRuntimeMethod::findVersionRuntime);
    }

    @Override
    public Uni<SelectVersionRuntimeResponse> selectVersionRuntime(@Valid final SelectVersionRuntimeRequest request) {
        return selectVersionRuntimeMethod.selectVersionRuntime(request);
    }

    @Override
    public Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(@Valid final ViewVersionRuntimesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::viewVersionRuntimes,
                viewVersionRuntimesMethod::viewVersionRuntimes);
    }

    @Override
    public Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(@Valid final DeleteVersionRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersionRuntime,
                deleteVersionRuntimeMethod::deleteVersionRuntime);
    }

    @Override
    public Uni<FindStageVersionIdResponse> findStageVersionId(@Valid final FindStageVersionIdRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::findStageVersionId,
                findStageVersionIdMethod::findStageVersionId);
    }
}
