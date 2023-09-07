package com.omgservers.module.user.impl.service.clientService.impl;

import com.omgservers.dto.user.DeleteClientResponse;
import com.omgservers.dto.user.DeleteClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.SyncClientResponse;
import com.omgservers.dto.user.SyncClientRequest;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.clientService.impl.method.deleteClient.DeleteClientMethod;
import com.omgservers.module.user.impl.service.clientService.impl.method.getClient.GetClientMethod;
import com.omgservers.module.user.impl.service.clientService.impl.method.syncClient.SyncClientMethod;
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
class ClientServiceImpl implements ClientService {

    final DeleteClientMethod deleteClientMethod;
    final SyncClientMethod syncClientMethod;
    final GetClientMethod getClientMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<SyncClientResponse> syncClient(SyncClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncClientRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncClient,
                syncClientMethod::syncClient);
    }

    @Override
    public Uni<GetClientResponse> getClient(GetClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetClientRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getClient,
                getClientMethod::getClient);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteClientRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deleteClient,
                deleteClientMethod::deleteClient);
    }
}
