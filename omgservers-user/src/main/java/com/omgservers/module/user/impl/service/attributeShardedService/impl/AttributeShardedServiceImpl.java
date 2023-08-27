package com.omgservers.module.user.impl.service.attributeShardedService.impl;

import com.omgservers.dto.user.DeleteAttributeShardResponse;
import com.omgservers.dto.user.DeleteAttributeShardedRequest;
import com.omgservers.dto.user.GetAttributeShardedResponse;
import com.omgservers.dto.user.GetAttributeShardedRequest;
import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
import com.omgservers.dto.user.SyncAttributeShardedResponse;
import com.omgservers.dto.user.SyncAttributeShardedRequest;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.service.attributeShardedService.AttributeShardedService;
import com.omgservers.module.user.impl.service.attributeShardedService.impl.method.deleteAttribute.DeleteAttributeMethod;
import com.omgservers.module.user.impl.service.attributeShardedService.impl.method.getAttribute.GetAttributeMethod;
import com.omgservers.module.user.impl.service.attributeShardedService.impl.method.getPlayerAttributes.GetPlayerAttributesMethod;
import com.omgservers.module.user.impl.service.attributeShardedService.impl.method.syncAttribute.SyncAttributeMethod;
import com.omgservers.module.user.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AttributeShardedServiceImpl implements AttributeShardedService {

    final GetPlayerAttributesMethod getPlayerAttributesMethod;
    final DeleteAttributeMethod deleteAttributeMethod;
    final SyncAttributeMethod syncAttributeMethod;
    final GetAttributeMethod getAttributeMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<GetAttributeShardedResponse> getAttribute(GetAttributeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetAttributeShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserServiceApi::getAttribute,
                getAttributeMethod::getAttribute);
    }

    @Override
    public Uni<GetPlayerAttributesShardedResponse> getPlayerAttributes(GetPlayerAttributesShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerAttributesShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserServiceApi::getPlayerAttributes,
                getPlayerAttributesMethod::getPlayerAttributes);
    }

    @Override
    public Uni<SyncAttributeShardedResponse> syncAttribute(SyncAttributeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncAttributeShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserServiceApi::syncAttribute,
                syncAttributeMethod::syncAttribute);
    }

    @Override
    public Uni<DeleteAttributeShardResponse> deleteAttribute(DeleteAttributeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteAttributeShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserServiceApi::deleteAttribute,
                deleteAttributeMethod::deleteAttribute);
    }
}
