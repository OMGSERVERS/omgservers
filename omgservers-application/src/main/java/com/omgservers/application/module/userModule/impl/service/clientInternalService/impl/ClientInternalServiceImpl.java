package com.omgservers.application.module.userModule.impl.service.clientInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.deleteClientMethod.DeleteClientMethod;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.getClientMethod.GetClientMethod;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.impl.method.syncClientMethod.SyncClientMethod;
import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.DeleteClientRoutedRequest;
import com.omgservers.dto.userModule.DeleteClientInternalResponse;
import com.omgservers.dto.userModule.GetClientRoutedRequest;
import com.omgservers.dto.userModule.GetClientInternalResponse;
import com.omgservers.dto.userModule.SyncClientRoutedRequest;
import com.omgservers.dto.userModule.SyncClientInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ClientInternalServiceImpl implements ClientInternalService {

    final DeleteClientMethod deleteClientMethod;
    final SyncClientMethod syncClientMethod;
    final GetClientMethod getClientMethod;

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<SyncClientInternalResponse> syncClient(SyncClientRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncClientRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::syncClient,
                syncClientMethod::syncClient);
    }

    @Override
    public Uni<GetClientInternalResponse> getClient(GetClientRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetClientRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::getClient,
                getClientMethod::getClient);
    }

    @Override
    public Uni<DeleteClientInternalResponse> deleteClient(DeleteClientRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteClientRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::deleteClient,
                deleteClientMethod::deleteClient);
    }
}
