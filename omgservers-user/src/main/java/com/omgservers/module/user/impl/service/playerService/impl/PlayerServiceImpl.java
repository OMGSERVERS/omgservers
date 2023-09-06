package com.omgservers.module.user.impl.service.playerService.impl;

import com.omgservers.dto.user.DeletePlayerShardedRequest;
import com.omgservers.dto.user.DeletePlayerShardedResponse;
import com.omgservers.dto.user.GetOrCreatePlayerHelpRequest;
import com.omgservers.dto.user.GetOrCreatePlayerHelpResponse;
import com.omgservers.dto.user.GetPlayerShardedRequest;
import com.omgservers.dto.user.GetPlayerShardedResponse;
import com.omgservers.dto.user.SyncPlayerShardedRequest;
import com.omgservers.dto.user.SyncPlayerShardedResponse;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.playerService.impl.method.deletePlayer.DeletePlayerMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.getOrCreatePlayer.GetOrCreatePlayerHelpMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.getPlayer.GetPlayerMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.syncPlayer.SyncPlayerMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerServiceImpl implements PlayerService {

    final DeletePlayerMethod deletePlayerMethod;
    final SyncPlayerMethod syncPlayerMethod;
    final GetPlayerMethod getPlayerMethod;

    final GetUserModuleClientOperation getUserModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final GetOrCreatePlayerHelpMethod getOrCreatePlayerHelpMethod;

    @Override
    public Uni<GetPlayerShardedResponse> getPlayer(GetPlayerShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetPlayerShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getPlayer,
                getPlayerMethod::getPlayer);
    }

    @Override
    public Uni<SyncPlayerShardedResponse> syncPlayer(SyncPlayerShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncPlayerShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncPlayer,
                syncPlayerMethod::syncPlayer);
    }

    @Override
    public Uni<DeletePlayerShardedResponse> deletePlayer(DeletePlayerShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeletePlayerShardedRequest::validate,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deletePlayer,
                deletePlayerMethod::deletePlayer);
    }

    @Override
    public Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request) {
        return getOrCreatePlayerHelpMethod.getOrCreatePlayer(request);
    }
}
