package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl;

import com.omgservers.application.module.versionModule.impl.operation.getVersionServiceApiClientOperation.GetVersionServiceApiClientOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.deleteVersionMethod.DeleteVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getBytecodeMethod.GetBytecodeMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getStageConfigMethod.GetStageConfigMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getVersionMethod.GetVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.syncVersionMethod.SyncVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi.VersionServiceApi;
import com.omgservers.base.impl.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import com.omgservers.dto.versionModule.DeleteVersionInternalRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeInternalRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigInternalRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionInternalRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionInternalRequest;
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
    public Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetVersionInternalRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getVersion,
                getVersionMethod::getVersion);
    }

    @Override
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncVersionInternalRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::syncVersion,
                syncVersionMethod::syncVersion);
    }

    @Override
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteVersionInternalRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetBytecodeInternalRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getBytecode,
                getBytecodeMethod::getBytecode);
    }

    @Override
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageConfigInternalRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getStageConfig,
                getStageConfigMethod::getStageConfig);
    }
}
