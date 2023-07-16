package com.omgservers.application.module.userModule.impl.service.playerHelpService.impl.method.getOrCreatePlayerHelpMethod;

import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.response.GetOrCreatePlayerHelpResponse;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.GetPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.request.SyncPlayerInternalRequest;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.response.GetPlayerInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class GetOrCreatePlayerHelpMethodImpl implements GetOrCreatePlayerHelpMethod {

    final PlayerInternalService playerInternalService;

    @Override
    public Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request) {
        GetOrCreatePlayerHelpRequest.validate(request);

        final var user = request.getUser();
        final var stage = request.getStage();
        return getPlayer(user, stage)
                .map(player -> new GetOrCreatePlayerHelpResponse(false, player))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> syncPlayer(user, stage)
                        .map(player -> new GetOrCreatePlayerHelpResponse(true, player)));
    }

    Uni<PlayerModel> getPlayer(UUID user, UUID stage) {
        final var request = new GetPlayerInternalRequest(user, stage);
        return playerInternalService.getPlayer(request)
                .map(GetPlayerInternalResponse::getPlayer);
    }

    Uni<PlayerModel> syncPlayer(UUID user, UUID stage) {
        final var player = PlayerModel.create(user, stage, PlayerConfigModel.create());
        final var request = new SyncPlayerInternalRequest(player);
        return playerInternalService.syncPlayer(request)
                .replaceWith(player);
    }
}
