package com.omgservers.module.tenant.impl.service.versionService.impl;

import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import com.omgservers.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.buildVersion.BuildVersionMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.deleteVersionMatchmaker.DeleteVersionMatchmakerMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.deleteVersionRuntime.DeleteVersionRuntimeMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.findStageVersionId.FindStageVersionIdMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.findVersionMatchmaker.FindVersionMatchmakerMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.findVersionRuntime.FindVersionRuntimeMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionBytecode.GetVersionBytecodeMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionConfig.GetVersionConfigMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionMatchmaker.GetVersionMatchmakerMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionRuntime.GetVersionRuntimeMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.selectVersionMatchmaker.SelectVersionMatchmakerMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.selectVersionRuntime.SelectVersionRuntimeMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersion.SyncVersionMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersionMatchmaker.SyncVersionMatchmakerMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersionRuntime.SyncVersionRuntimeMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakers.ViewVersionMatchmakersMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.viewVersionRuntimes.ViewVersionRuntimesMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
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
    final GetVersionBytecodeMethod getVersionBytecodeMethod;
    final FindStageVersionIdMethod findStageVersionIdMethod;
    final GetVersionMatchmakerMethod getVersionMatchmaker;
    final GetVersionRuntimeMethod getVersionRuntimeMethod;
    final GetVersionConfigMethod getVersionConfigMethod;
    final DeleteVersionMethod deleteVersionMethod;
    final BuildVersionMethod buildVersionMethod;
    final SyncVersionMethod syncVersionMethod;
    final GetVersionMethod getVersionMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<BuildVersionResponse> buildVersion(@Valid final BuildVersionRequest request) {
        return buildVersionMethod.buildVersion(request);
    }

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
    public Uni<DeleteVersionResponse> deleteVersion(@Valid final DeleteVersionRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetVersionBytecodeResponse> getVersionBytecode(@Valid final GetVersionBytecodeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionBytecode,
                getVersionBytecodeMethod::getVersionBytecode);
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
