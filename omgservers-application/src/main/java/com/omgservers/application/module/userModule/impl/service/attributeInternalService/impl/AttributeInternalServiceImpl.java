package com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.deleteAttributeMethod.DeleteAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getAttributeMethod.GetAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.getPlayerAttributesMethod.GetPlayerAttributesMethod;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.impl.method.syncAttributeMethod.SyncAttributeMethod;
import com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.DeleteAttributeRoutedRequest;
import com.omgservers.dto.userModule.DeleteAttributeInternalResponse;
import com.omgservers.dto.userModule.GetAttributeRoutedRequest;
import com.omgservers.dto.userModule.GetAttributeInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.dto.userModule.SyncAttributeRoutedRequest;
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
    public Uni<GetAttributeInternalResponse> getAttribute(GetAttributeRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetAttributeRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::getAttribute,
                getAttributeMethod::getAttribute);
    }

    @Override
    public Uni<GetPlayerAttributesInternalResponse> getPlayerAttributes(GetPlayerAttributesRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerAttributesRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::getPlayerAttributes,
                getPlayerAttributesMethod::getPlayerAttributes);
    }

    @Override
    public Uni<SyncAttributeInternalResponse> syncAttribute(SyncAttributeRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncAttributeRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::syncAttribute,
                syncAttributeMethod::syncAttribute);
    }

    @Override
    public Uni<DeleteAttributeInternalResponse> deleteAttribute(DeleteAttributeRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteAttributeRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApi::deleteAttribute,
                deleteAttributeMethod::deleteAttribute);
    }
}
