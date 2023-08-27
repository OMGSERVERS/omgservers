package com.omgservers.module.user.impl.service.objectShardedService.impl;

import com.omgservers.dto.user.DeleteObjectShardedResponse;
import com.omgservers.dto.user.DeleteObjectShardedRequest;
import com.omgservers.dto.user.GetObjectShardedResponse;
import com.omgservers.dto.user.GetObjectShardedRequest;
import com.omgservers.dto.user.SyncObjectShardedResponse;
import com.omgservers.dto.user.SyncObjectShardedRequest;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.module.user.impl.service.objectShardedService.ObjectShardedService;
import com.omgservers.module.user.impl.service.objectShardedService.impl.method.deleteObject.DeleteObjectMethod;
import com.omgservers.module.user.impl.service.objectShardedService.impl.method.getObject.GetObjectMethod;
import com.omgservers.module.user.impl.service.objectShardedService.impl.method.syncObject.SyncObjectMethod;
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
class ObjectShardedServiceImpl implements ObjectShardedService {

    final DeleteObjectMethod deleteObjectMethod;
    final SyncObjectMethod syncObjectMethod;
    final GetObjectMethod getObjectMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetObjectShardedResponse> getObject(GetObjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetObjectShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getObject,
                getObjectMethod::getObject);
    }

    @Override
    public Uni<SyncObjectShardedResponse> syncObject(SyncObjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncObjectShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncObject,
                syncObjectMethod::syncObject);
    }

    @Override
    public Uni<DeleteObjectShardedResponse> deleteObject(DeleteObjectShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteObjectShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deleteObject,
                deleteObjectMethod::deleteObject);
    }
}
