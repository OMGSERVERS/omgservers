package com.omgservers.module.user.impl.service.attributeService.impl;

import com.omgservers.dto.user.DeleteAttributeResponse;
import com.omgservers.dto.user.DeleteAttributeRequest;
import com.omgservers.dto.user.GetAttributeResponse;
import com.omgservers.dto.user.GetAttributeRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.SyncAttributeResponse;
import com.omgservers.dto.user.SyncAttributeRequest;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.service.attributeService.AttributeService;
import com.omgservers.module.user.impl.service.attributeService.impl.method.deleteAttribute.DeleteAttributeMethod;
import com.omgservers.module.user.impl.service.attributeService.impl.method.getAttribute.GetAttributeMethod;
import com.omgservers.module.user.impl.service.attributeService.impl.method.getPlayerAttributes.GetPlayerAttributesMethod;
import com.omgservers.module.user.impl.service.attributeService.impl.method.syncAttribute.SyncAttributeMethod;
import com.omgservers.module.user.impl.service.webService.impl.serviceApi.UserApi;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AttributeServiceImpl implements AttributeService {

    final GetPlayerAttributesMethod getPlayerAttributesMethod;
    final DeleteAttributeMethod deleteAttributeMethod;
    final SyncAttributeMethod syncAttributeMethod;
    final GetAttributeMethod getAttributeMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    @Override
    public Uni<GetAttributeResponse> getAttribute(GetAttributeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetAttributeRequest::validate,
                getUserModuleClientOperation::getClient,
                UserApi::getAttribute,
                getAttributeMethod::getAttribute);
    }

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(GetPlayerAttributesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerAttributesRequest::validate,
                getUserModuleClientOperation::getClient,
                UserApi::getPlayerAttributes,
                getPlayerAttributesMethod::getPlayerAttributes);
    }

    @Override
    public Uni<SyncAttributeResponse> syncAttribute(SyncAttributeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncAttributeRequest::validate,
                getUserModuleClientOperation::getClient,
                UserApi::syncAttribute,
                syncAttributeMethod::syncAttribute);
    }

    @Override
    public Uni<DeleteAttributeResponse> deleteAttribute(DeleteAttributeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteAttributeRequest::validate,
                getUserModuleClientOperation::getClient,
                UserApi::deleteAttribute,
                deleteAttributeMethod::deleteAttribute);
    }
}
