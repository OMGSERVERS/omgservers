package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.deletePlayerMethod.DeletePlayerMethod;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.getPlayerMethod.GetPlayerMethod;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.syncPlayerMethod.SyncPlayerMethod;
import com.omgservers.operation.calculateShard.CalculateShardOperation;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.DeletePlayerShardRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetPlayerShardRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerShardRequest;
import com.omgservers.dto.userModule.SyncPlayerInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerInternalServiceImpl implements PlayerInternalService {

    final DeletePlayerMethod deletePlayerMethod;
    final SyncPlayerMethod syncPlayerMethod;
    final GetPlayerMethod getPlayerMethod;

    final GetUserServiceApiClientOperation getUserServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::getPlayer,
                getPlayerMethod::getPlayer);
    }

    @Override
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncPlayerShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::syncPlayer,
                syncPlayerMethod::syncPlayer);
    }

    @Override
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeletePlayerShardRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::deletePlayer,
                deletePlayerMethod::deletePlayer);
    }
}
