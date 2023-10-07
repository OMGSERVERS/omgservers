package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.user.GetPlayerRequest;
import com.omgservers.dto.user.GetPlayerResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchmakerCommandModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        final var body = (ClientDeletedEventBodyModel) event.getBody();
        final var client = body.getClient();
        final var userId = client.getUserId();
        final var playerId = client.getPlayerId();

        return getPlayer(userId, playerId)
                .flatMap(player -> {
                    final var tenantId = player.getTenantId();
                    final var stageId = player.getStageId();
                    return getStage(tenantId, stageId)
                            .flatMap(stage -> {
                                final var matchmakerId = stage.getMatchmakerId();
                                final var clientId = client.getId();
                                return syncDeleteClientMatchmakerCommand(matchmakerId, clientId);
                            });
                })
                .replaceWith(true);
    }

    Uni<PlayerModel> getPlayer(final Long userId, final Long playerId) {
        final var request = new GetPlayerRequest(userId, playerId);
        return userModule.getPlayerService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }

    Uni<StageModel> getStage(final Long tenantId, final Long stageId) {
        final var request = new GetStageRequest(tenantId, stageId);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<Void> syncDeleteClientMatchmakerCommand(final Long matchmakerId, final Long clientId) {
        final var commandBody = new DeleteClientMatchmakerCommandBodyModel(clientId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService()
                .syncMatchmakerCommand(request)
                .replaceWithVoid();
    }
}

