package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl;

import com.omgservers.application.module.versionModule.impl.operation.getVersionServiceApiClientOperation.GetVersionServiceApiClientOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.deleteVersionMethod.DeleteVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getBytecodeMethod.GetBytecodeMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getStageConfigMethod.GetStageConfigMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getVersionMethod.GetVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.syncVersionMethod.SyncVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi.VersionServiceApi;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.versionModule.DeleteVersionShardRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeShardRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigShardRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionShardRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionShardRequest;
import com.omgservers.dto.versionModule.SyncVersionInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class VersionInternalServiceImpl implements VersionInternalService {

    final GetStageConfigMethod getStageConfigMethod;
    final DeleteVersionMethod deleteVersionMethod;
    final SyncVersionMethod syncVersionMethod;
    final GetBytecodeMethod getBytecodeMethod;
    final GetVersionMethod getVersionMethod;

    final GetVersionServiceApiClientOperation getVersionServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetVersionInternalResponse> getVersion(GetVersionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetVersionShardRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getVersion,
                getVersionMethod::getVersion);
    }

    @Override
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncVersionShardRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::syncVersion,
                syncVersionMethod::syncVersion);
    }

    @Override
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteVersionShardRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetBytecodeShardRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getBytecode,
                getBytecodeMethod::getBytecode);
    }

    @Override
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageConfigShardRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getStageConfig,
                getStageConfigMethod::getStageConfig);
    }
}
