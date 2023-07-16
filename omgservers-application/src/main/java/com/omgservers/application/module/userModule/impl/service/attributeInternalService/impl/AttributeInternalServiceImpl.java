package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod.DeleteAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getAttributeMethod.GetAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getPlayerAttributesMethod.GetPlayerAttributesMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod.SyncAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.DeleteAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetPlayerAttributesInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.SyncAttributeInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.DeleteAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetAttributeInternalResponse;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.SyncAttributeInternalResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
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
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetAttributeInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::getAttribute,
                getAttributeMethod::getAttribute);
    }

    @Override
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerAttributesInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::getPlayerAttributes,
                getPlayerAttributesMethod::getPlayerAttributes);
    }

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncAttributeInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::syncAttribute,
                syncAttributeMethod::syncAttribute);
    }

    @Override
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteAttributeInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::deleteAttribute,
                deleteAttributeMethod::deleteAttribute);
    }
}
