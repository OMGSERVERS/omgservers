package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl;

import com.omgservers.application.module.versionModule.impl.operation.getVersionServiceApiClientOperation.GetVersionServiceApiClientOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.VersionInternalService;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.deleteVersionMethod.DeleteVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getBytecodeMethod.GetBytecodeMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getStageConfigMethod.GetStageConfigMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getVersionMethod.GetVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.syncVersionMethod.SyncVersionMethod;
import com.omgservers.application.module.versionModule.impl.service.versionWebService.impl.serviceApi.VersionServiceApi;
import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.versionModule.DeleteVersionRoutedRequest;
import com.omgservers.dto.versionModule.DeleteVersionInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeRoutedRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.dto.versionModule.GetStageConfigRoutedRequest;
import com.omgservers.dto.versionModule.GetStageConfigInternalResponse;
import com.omgservers.dto.versionModule.GetVersionRoutedRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
import com.omgservers.dto.versionModule.SyncVersionRoutedRequest;
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
    public Uni<GetVersionInternalResponse> getVersion(GetVersionRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetVersionRoutedRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getVersion,
                getVersionMethod::getVersion);
    }

    @Override
    public Uni<SyncVersionInternalResponse> syncVersion(SyncVersionRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncVersionRoutedRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::syncVersion,
                syncVersionMethod::syncVersion);
    }

    @Override
    public Uni<DeleteVersionInternalResponse> deleteVersion(DeleteVersionRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteVersionRoutedRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::deleteVersion,
                deleteVersionMethod::deleteVersion);
    }

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetBytecodeRoutedRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getBytecode,
                getBytecodeMethod::getBytecode);
    }

    @Override
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetStageConfigRoutedRequest::validate,
                getVersionServiceApiClientOperation::getClient,
                VersionServiceApi::getStageConfig,
                getStageConfigMethod::getStageConfig);
    }
}
