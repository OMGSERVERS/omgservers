package com.omgservers.service.module.user.impl.service.clientService.impl;

import com.omgservers.model.dto.user.DeleteClientRequest;
import com.omgservers.model.dto.user.DeleteClientResponse;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.SyncClientRequest;
import com.omgservers.model.dto.user.SyncClientResponse;
import com.omgservers.service.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.service.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.service.module.user.impl.service.clientService.ClientService;
import com.omgservers.service.module.user.impl.service.clientService.impl.method.deleteClient.DeleteClientMethod;
import com.omgservers.service.module.user.impl.service.clientService.impl.method.getClient.GetClientMethod;
import com.omgservers.service.module.user.impl.service.clientService.impl.method.syncClient.SyncClientMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
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
    public Uni<SyncClientResponse> syncClient(@Valid final SyncClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncClient,
                syncClientMethod::syncClient);
    }

    @Override
    public Uni<GetClientResponse> getClient(@Valid final GetClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getClient,
                getClientMethod::getClient);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(@Valid final DeleteClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deleteClient,
                deleteClientMethod::deleteClient);
    }
}
