package com.omgservers.module.tenant.impl.service.versionShardedService.impl;

import com.omgservers.dto.tenant.DeleteVersionShardedRequest;
import com.omgservers.dto.tenant.DeleteVersionShardedResponse;
import com.omgservers.dto.tenant.GetCurrentVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetCurrentVersionIdShardedResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeShardedRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeShardedResponse;
import com.omgservers.dto.tenant.GetVersionConfigShardedRequest;
import com.omgservers.dto.tenant.GetVersionConfigShardedResponse;
import com.omgservers.dto.tenant.GetVersionShardedRequest;
import com.omgservers.dto.tenant.GetVersionShardedResponse;
import com.omgservers.dto.tenant.SyncVersionShardedRequest;
import com.omgservers.dto.tenant.SyncVersionShardedResponse;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.GetTenantModuleClientOperation;
import com.omgservers.module.tenant.impl.operation.getTenantModuleClient.TenantModuleClient;
import com.omgservers.module.tenant.impl.service.versionShardedService.VersionShardedService;
import com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getCurrentVersionId.GetCurrentVersionIdMethod;
import com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getVersionBytecode.GetVersionBytecodeMethod;
import com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getVersionConfig.GetVersionConfigMethod;
import com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.syncVersion.SyncVersionMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionShardedServiceImpl implements VersionShardedService {

    final GetVersionBytecodeMethod getVersionBytecodeMethod;
    final GetVersionConfigMethod getVersionConfigMethod;
    final GetCurrentVersionIdMethod getCurrentVersionId;
    final DeleteVersionMethod deleteVersionMethod;
    final SyncVersionMethod syncVersionMethod;
    final GetVersionMethod getVersionMethod;

    final GetTenantModuleClientOperation getTenantModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetVersionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersion,
                getVersionMethod::getVersion);
    }

    @Override
    public Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncVersionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::syncVersion,
                syncVersionMethod::syncVersion);
    }

    @Override
    public Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteVersionShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetVersionBytecodeShardedResponse> getVersionBytecode(GetVersionBytecodeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetVersionBytecodeShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionBytecode,
                getVersionBytecodeMethod::getVersionBytecode);
    }

    @Override
    public Uni<GetVersionConfigShardedResponse> getVersionConfig(GetVersionConfigShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetVersionConfigShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getVersionConfig,
                getVersionConfigMethod::getVersionConfig);
    }

    @Override
    public Uni<GetCurrentVersionIdShardedResponse> getCurrentVersionId(GetCurrentVersionIdShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetCurrentVersionIdShardedRequest::validate,
                getTenantModuleClientOperation::getClient,
                TenantModuleClient::getCurrentVersionId,
                getCurrentVersionId::getCurrentVersionId);
    }
}
