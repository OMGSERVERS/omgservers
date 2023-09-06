package com.omgservers.module.user.impl.service.clientService.impl;

import com.omgservers.dto.user.DeleteClientShardedResponse;
import com.omgservers.dto.user.DeleteClientShardedRequest;
import com.omgservers.dto.user.GetClientShardedResponse;
import com.omgservers.dto.user.GetClientShardedRequest;
import com.omgservers.dto.user.SyncClientShardedResponse;
import com.omgservers.dto.user.SyncClientShardedRequest;
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
    public Uni<SyncClientShardedResponse> syncClient(SyncClientShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncClientShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncClient,
                syncClientMethod::syncClient);
    }

    @Override
    public Uni<GetClientShardedResponse> getClient(GetClientShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetClientShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getClient,
                getClientMethod::getClient);
    }

    @Override
    public Uni<DeleteClientShardedResponse> deleteClient(DeleteClientShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteClientShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deleteClient,
                deleteClientMethod::deleteClient);
    }
}
