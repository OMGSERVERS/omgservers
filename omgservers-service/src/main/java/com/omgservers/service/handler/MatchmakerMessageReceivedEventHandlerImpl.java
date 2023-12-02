package com.omgservers.service.handler;

import com.omgservers.model.dto.matchmaker.SyncRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncRequestResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.service.factory.RequestModelFactory;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerMessageReceivedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GenerateIdOperation generateIdOperation;

    final RequestModelFactory requestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MESSAGE_RECEIVED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMessageReceivedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();
        final var mode = body.getMode();

        return userModule.getShortcutService().getClient(userId, clientId)
                .flatMap(client -> {
                    final var matchmakerId = client.getDefaultMatchmakerId();
                    log.info("Matchmaker was requested, " +
                                    "id={}, " +
                                    "mode={}, " +
                                    "client={}/{}, " +
                                    "stage={}/{}",
                            matchmakerId,
                            mode,
                            userId,
                            clientId,
                            tenantId,
                            stageId);

                    return userModule.getShortcutService().getPlayerAttributes(userId, playerId)
                            .flatMap(attributes -> syncMatchmakerRequest(matchmakerId,
                                    userId,
                                    clientId,
                                    mode,
                                    attributes));
                });
    }

    Uni<Boolean> syncMatchmakerRequest(final Long matchmakerId,
                                       final Long userId,
                                       final Long clientId,
                                       final String mode,
                                       final PlayerAttributesModel attributes) {
        final var requestConfig = new RequestConfigModel(attributes);
        final var requestModel = requestModelFactory.create(
                matchmakerId,
                userId,
                clientId,
                mode,
                requestConfig);
        final var request = new SyncRequestRequest(requestModel);
        return matchmakerModule.getMatchmakerService().syncRequest(request)
                .map(SyncRequestResponse::getCreated);
    }
}
