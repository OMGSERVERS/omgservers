package com.omgservers.module.tenant.impl.service.versionService.impl;

import com.omgservers.dto.tenant.BuildVersionRequest;
import com.omgservers.dto.tenant.BuildVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.versionService.VersionService;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.buildVersion.BuildVersionMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getStageVersionId.GetStageVersionIdMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionBytecode.GetVersionBytecodeMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionConfig.GetVersionConfigMethod;
import com.omgservers.module.tenant.impl.service.versionService.impl.method.syncVersion.SyncVersionMethod;
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

    final GetVersionBytecodeMethod getVersionBytecodeMethod;
    final GetVersionConfigMethod getVersionConfigMethod;
    final GetStageVersionIdMethod getStageVersionIdMethod;
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
    public Uni<GetStageVersionIdResponse> getStageVersionId(@Valid final GetStageVersionIdRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getStageVersionId,
                getStageVersionIdMethod::getStageVersionId);
    }
}
