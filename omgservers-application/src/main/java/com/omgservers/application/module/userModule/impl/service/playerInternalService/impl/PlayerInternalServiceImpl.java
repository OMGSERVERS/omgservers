package com.omgservers.application.module.userModule.impl.service.playerInternalService.impl;

import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.GetUserServiceApiClientOperation;
import com.omgservers.application.module.userModule.impl.operation.getUserServiceApiClientOperation.UserServiceApiClient;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.deletePlayerMethod.DeletePlayerMethod;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.getPlayerMethod.GetPlayerMethod;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.impl.method.syncPlayerMethod.SyncPlayerMethod;
import com.omgservers.base.operation.calculateShard.CalculateShardOperation;
import com.omgservers.base.operation.handleInternalRequest.HandleInternalRequestOperation;
import com.omgservers.dto.userModule.DeletePlayerRoutedRequest;
import com.omgservers.dto.userModule.DeletePlayerInternalResponse;
import com.omgservers.dto.userModule.GetPlayerRoutedRequest;
import com.omgservers.dto.userModule.GetPlayerInternalResponse;
import com.omgservers.dto.userModule.SyncPlayerRoutedRequest;
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
    public Uni<GetPlayerInternalResponse> getPlayer(GetPlayerRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::getPlayer,
                getPlayerMethod::getPlayer);
    }

    @Override
    public Uni<SyncPlayerInternalResponse> syncPlayer(SyncPlayerRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncPlayerRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::syncPlayer,
                syncPlayerMethod::syncPlayer);
    }

    @Override
    public Uni<DeletePlayerInternalResponse> deletePlayer(DeletePlayerRoutedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeletePlayerRoutedRequest::validate,
                getUserServiceApiClientOperation::getClient,
                UserServiceApiClient::deletePlayer,
                deletePlayerMethod::deletePlayer);
    }
}
