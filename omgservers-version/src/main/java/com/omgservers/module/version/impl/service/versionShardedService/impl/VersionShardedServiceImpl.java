package com.omgservers.module.version.impl.service.versionShardedService.impl;

import com.omgservers.module.version.impl.operation.getVersionModuleClient.GetVersionModuleClientOperation;
import com.omgservers.module.version.impl.service.versionShardedService.VersionShardedService;
import com.omgservers.module.version.impl.service.versionShardedService.impl.method.deleteVersion.DeleteVersionMethod;
import com.omgservers.module.version.impl.service.versionShardedService.impl.method.getBytecode.GetBytecodeMethod;
import com.omgservers.module.version.impl.service.versionShardedService.impl.method.getStageConfig.GetStageConfigMethod;
import com.omgservers.module.version.impl.service.versionShardedService.impl.method.getVersion.GetVersionMethod;
import com.omgservers.module.version.impl.service.versionShardedService.impl.method.syncVersion.SyncVersionMethod;
import com.omgservers.module.version.impl.service.versionWebService.impl.serviceApi.VersionServiceApi;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.version.DeleteVersionShardedRequest;
import com.omgservers.dto.version.DeleteVersionShardedResponse;
import com.omgservers.dto.version.GetBytecodeShardedRequest;
import com.omgservers.dto.version.GetBytecodeShardedResponse;
import com.omgservers.dto.version.GetStageConfigShardedRequest;
import com.omgservers.dto.version.GetStageConfigShardedResponse;
import com.omgservers.dto.version.GetVersionShardedRequest;
import com.omgservers.dto.version.GetVersionShardedResponse;
import com.omgservers.dto.version.SyncVersionShardedRequest;
import com.omgservers.dto.version.SyncVersionShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionShardedServiceImpl implements VersionShardedService {

    final GetStageConfigMethod getStageConfigMethod;
    final DeleteVersionMethod deleteVersionMethod;
    final SyncVersionMethod syncVersionMethod;
    final GetBytecodeMethod getBytecodeMethod;
    final GetVersionMethod getVersionMethod;

    final GetVersionModuleClientOperation getVersionModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetVersionShardedRequest::validate,
                getVersionModuleClientOperation::getClient,
                VersionServiceApi::getVersion,
                getVersionMethod::getVersion);
    }

    @Override
    public Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncVersionShardedRequest::validate,
                getVersionModuleClientOperation::getClient,
                VersionServiceApi::syncVersion,
                syncVersionMethod::syncVersion);
    }

    @Override
    public Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteVersionShardedRequest::validate,
                getVersionModuleClientOperation::getClient,
                VersionServiceApi::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetBytecodeShardedResponse> getBytecode(GetBytecodeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetBytecodeShardedRequest::validate,
                getVersionModuleClientOperation::getClient,
                VersionServiceApi::getBytecode,
                getBytecodeMethod::getBytecode);
    }

    @Override
    public Uni<GetStageConfigShardedResponse> getStageConfig(GetStageConfigShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageConfigShardedRequest::validate,
                getVersionModuleClientOperation::getClient,
                VersionServiceApi::getStageConfig,
                getStageConfigMethod::getStageConfig);
    }
}
