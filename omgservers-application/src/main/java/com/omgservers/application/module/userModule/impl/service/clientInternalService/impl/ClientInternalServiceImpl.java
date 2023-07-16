package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.createClientMethod.CreateClientMethod;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod.DeleteClientMethod;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.getClientMethod.GetClientMethod;
import com.omgservers.application.module.userModule.impl.service.userWebService.impl.serviceApi.UserServiceApi;
import com.omgservers.application.operation.calculateShardOperation.CalculateShardOperation;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.CreateClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.DeleteClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.request.GetClientInternalRequest;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.CreateClientInternalResponse;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.response.GetClientInternalResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClientInternalServiceImpl implements ClientInternalService {

    final CreateClientMethod createClientMethod;
    final DeleteClientMethod deleteClientMethod;
    final GetClientMethod getClientMethod;

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<CreateClientInternalResponse> createClient(CreateClientInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CreateClientInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::createClient,
                createClientMethod::createClient);
    }

    @Override
    public Uni<GetClientInternalResponse> getClient(GetClientInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetClientInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::getClient,
                getClientMethod::getClient);
    }

    @Override
    public Uni<Void> deleteClient(DeleteClientInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteClientInternalRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::deleteClient,
                deleteClientMethod::deleteClient);
    }
}
