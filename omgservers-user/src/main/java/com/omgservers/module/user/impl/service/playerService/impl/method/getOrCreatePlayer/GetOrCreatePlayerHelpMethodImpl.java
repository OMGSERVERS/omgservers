package com.omgservers.module.user.impl.service.playerService.impl.method.getOrCreatePlayer;

import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.SyncPlayerRequest;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.module.user.factory.PlayerModelFactory;
import com.omgservers.dto.user.GetOrCreatePlayerRequest;
import com.omgservers.dto.user.GetOrCreatePlayerResponse;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class GetOrCreatePlayerHelpMethodImpl implements GetOrCreatePlayerHelpMethod {

    final PlayerService playerService;

    final PlayerModelFactory playerModelFactory;
    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<GetOrCreatePlayerResponse> getOrCreatePlayer(GetOrCreatePlayerRequest request) {
        GetOrCreatePlayerRequest.validate(request);

        final var userId = request.getUserId();
        final var stageId = request.getStageId();
        return getPlayer(userId, stageId)
                .map(player -> new GetOrCreatePlayerResponse(false, player))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> syncPlayer(userId, stageId)
                        .map(player -> new GetOrCreatePlayerResponse(true, player)));
    }

    Uni<PlayerModel> getPlayer(Long userId, Long stageId) {
        final var request = new GetPlayerRequest(userId, stageId);
        return playerService.getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }

    Uni<PlayerModel> syncPlayer(Long userId, Long stageId) {
        final var player = playerModelFactory
                .create(userId, stageId, PlayerConfigModel.create());
        final var request = new SyncPlayerRequest(player);
        return playerService.syncPlayer(request)
                .replaceWith(player);
    }
}
