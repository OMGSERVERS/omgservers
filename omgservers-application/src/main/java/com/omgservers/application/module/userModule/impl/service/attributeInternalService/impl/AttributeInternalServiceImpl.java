package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod.DeleteAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getAttributeMethod.GetAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getPlayerAttributesMethod.GetPlayerAttributesMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod.SyncAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.DeleteAttributeShardRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.GetAttributeShardRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesShardRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeShardRequest;
import com.omgservers.dto.userModule.SyncAttributeInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AttributeInternalServiceImpl implements AttributeInternalService {

    final GetPlayerAttributesMethod getPlayerAttributesMethod;
    final DeleteAttributeMethod deleteAttributeMethod;
    final SyncAttributeMethod syncAttributeMethod;
    final GetAttributeMethod getAttributeMethod;

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetAttributeShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::getAttribute,
                getAttributeMethod::getAttribute);
    }

    @Override
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerAttributesShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::getPlayerAttributes,
                getPlayerAttributesMethod::getPlayerAttributes);
    }

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncAttributeShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::syncAttribute,
                syncAttributeMethod::syncAttribute);
    }

    @Override
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteAttributeShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::deleteAttribute,
                deleteAttributeMethod::deleteAttribute);
    }
}
