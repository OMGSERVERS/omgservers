package com.omgservers.module.user.impl.service.playerService.impl;

import com.omgservers.model.dto.user.DeletePlayerRequest;
import com.omgservers.model.dto.user.DeletePlayerResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.dto.user.GetPlayerObjectRequest;
import com.omgservers.model.dto.user.GetPlayerObjectResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.dto.user.SyncPlayerResponse;
import com.omgservers.model.dto.user.UpdatePlayerAttributesRequest;
import com.omgservers.model.dto.user.UpdatePlayerAttributesResponse;
import com.omgservers.model.dto.user.UpdatePlayerObjectRequest;
import com.omgservers.model.dto.user.UpdatePlayerObjectResponse;
import com.omgservers.module.user.impl.operation.getUserModuleClient.GetUserModuleClientOperation;
import com.omgservers.module.user.impl.operation.getUserModuleClient.UserModuleClient;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.playerService.impl.method.deletePlayer.DeletePlayerMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.findPlayer.FindPlayerMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.getPlayer.GetPlayerMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.getPlayerAttributes.GetPlayerAttributesMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.getPlayerObject.GetPlayerObjectMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.syncPlayer.SyncPlayerMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.updatePlayerAttributes.UpdatePlayerAttributesMethod;
import com.omgservers.module.user.impl.service.playerService.impl.method.updatePlayerObject.UpdatePlayerObjectMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerServiceImpl implements PlayerService {

    final UpdatePlayerAttributesMethod updatePlayerAttributes;
    final GetPlayerAttributesMethod getPlayerAttributesMethod;
    final UpdatePlayerObjectMethod updatePlayerObjectMethod;
    final GetPlayerObjectMethod getPlayerObjectMethod;
    final DeletePlayerMethod deletePlayerMethod;
    final SyncPlayerMethod syncPlayerMethod;
    final FindPlayerMethod findPlayerMethod;
    final GetPlayerMethod getPlayerMethod;

    final HandleInternalRequestOperation handleInternalRequestOperation;
    final GetUserModuleClientOperation getUserModuleClientOperation;

    @Override
    public Uni<GetPlayerResponse> getPlayer(@Valid final GetPlayerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getPlayer,
                getPlayerMethod::getPlayer);
    }

    @Override
    public Uni<GetPlayerAttributesResponse> getPlayerAttributes(@Valid final GetPlayerAttributesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getPlayerAttributes,
                getPlayerAttributesMethod::getPlayerAttributes);
    }

    @Override
    public Uni<GetPlayerObjectResponse> getPlayerObject(@Valid final GetPlayerObjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::getPlayerObject,
                getPlayerObjectMethod::getPlayerObject);
    }

    @Override
    public Uni<FindPlayerResponse> findPlayer(@Valid final FindPlayerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::findPlayer,
                findPlayerMethod::findPlayer);
    }

    @Override
    public Uni<SyncPlayerResponse> syncPlayer(@Valid final SyncPlayerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::syncPlayer,
                syncPlayerMethod::syncPlayer);
    }

    @Override
    public Uni<UpdatePlayerAttributesResponse> updatePlayerAttributes(
            @Valid final UpdatePlayerAttributesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::updatePlayerAttributes,
                updatePlayerAttributes::updatePlayerAttributes);
    }

    @Override
    public Uni<UpdatePlayerObjectResponse> updatePlayerObject(@Valid final UpdatePlayerObjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::updatePlayerObject,
                updatePlayerObjectMethod::updatePlayerObject);
    }

    @Override
    public Uni<DeletePlayerResponse> deletePlayer(@Valid final DeletePlayerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getUserModuleClientOperation::getClient,
                UserModuleClient::deletePlayer,
                deletePlayerMethod::deletePlayer);
    }
}
