package com.omgservers.module.user.impl.service.objectService.impl;

import com.omgservers.dto.user.DeleteObjectRequest;
import com.omgservers.dto.user.DeleteObjectResponse;
import com.omgservers.dto.user.GetObjectRequest;
import com.omgservers.dto.user.GetObjectResponse;
import com.omgservers.dto.user.SyncObjectRequest;
import com.omgservers.dto.user.SyncObjectResponse;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.module.user.impl.service.objectService.ObjectService;
import com.omgservers.module.user.impl.service.objectService.impl.method.deleteObject.DeleteObjectMethod;
import com.omgservers.module.user.impl.service.objectService.impl.method.getObject.GetObjectMethod;
import com.omgservers.module.user.impl.service.objectService.impl.method.syncObject.SyncObjectMethod;
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
class ObjectServiceImpl implements ObjectService {

    final DeleteObjectMethod deleteObjectMethod;
    final SyncObjectMethod syncObjectMethod;
    final GetObjectMethod getObjectMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetObjectResponse> getObject(@Valid final GetObjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getObject,
                getObjectMethod::getObject);
    }

    @Override
    public Uni<SyncObjectResponse> syncObject(@Valid final SyncObjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncObject,
                syncObjectMethod::syncObject);
    }

    @Override
    public Uni<DeleteObjectResponse> deleteObject(@Valid final DeleteObjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deleteObject,
                deleteObjectMethod::deleteObject);
    }
}
