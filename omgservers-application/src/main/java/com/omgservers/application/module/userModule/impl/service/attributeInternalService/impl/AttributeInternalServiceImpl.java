package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod.DeleteAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getAttributeMethod.GetAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getPlayerAttributesMethod.GetPlayerAttributesMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod.SyncAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.DeleteAttributeInternalRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.GetAttributeInternalRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeInternalRequest;
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
