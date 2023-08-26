package com.omgservers.application.module.userModule.impl.service.objectInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.ObjectInternalService;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.deleteObjectMethod.DeleteObjectMethod;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.getObjectMethod.GetObjectMethod;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.impl.method.syncObjectMethod.SyncObjectMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.DeleteObjectShardRequest;
import com.omgservers.dto.userModule.DeleteObjectInternalResponse;
import com.omgservers.dto.userModule.GetObjectShardRequest;
import com.omgservers.dto.userModule.GetObjectInternalResponse;
import com.omgservers.dto.userModule.SyncObjectShardRequest;
import com.omgservers.dto.userModule.SyncObjectInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ObjectInternalServiceImpl implements ObjectInternalService {

    final DeleteObjectMethod deleteObjectMethod;
    final SyncObjectMethod syncObjectMethod;
    final GetObjectMethod getObjectMethod;

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetObjectInternalResponse> getObject(GetObjectShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetObjectShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::getObject,
                getObjectMethod::getObject);
    }

    @Override
    public Uni<SyncObjectInternalResponse> syncObject(SyncObjectShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncObjectShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::syncObject,
                syncObjectMethod::syncObject);
    }

    @Override
    public Uni<DeleteObjectInternalResponse> deleteObject(DeleteObjectShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteObjectShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::deleteObject,
                deleteObjectMethod::deleteObject);
    }
}
