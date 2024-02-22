package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.matchmaker.SyncRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncRequestResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.RequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
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
    final ClientModule clientModule;
    final UserModule userModule;

    final GenerateIdOperation generateIdOperation;

    final RequestModelFactory requestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_MESSAGE_RECEIVED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (MatchmakerMessageReceivedEventBodyModel) event.getBody();

        final var clientId = body.getClientId();
        final var message = body.getMessage();

        if (message.getBody() instanceof final MatchmakerMessageBodyModel messageBody) {
            return getClient(clientId)
                    .flatMap(client -> {
                        final var matchmakerId = client.getMatchmakerId();
                        final var tenantId = client.getTenantId();
                        final var versionId = client.getVersionId();

                        final var mode = messageBody.getMode();

                        log.info("Matchmaker was requested, " +
                                        "id={}, " +
                                        "mode={}, " +
                                        "clientId={}, " +
                                        "version={}/{}",
                                matchmakerId,
                                mode,
                                clientId,
                                tenantId,
                                versionId);

                        final var userId = client.getUserId();
                        final var playerId = client.getPlayerId();

                        return getPlayerAttributes(userId, playerId)
                                .flatMap(attributes -> syncMatchmakerRequest(matchmakerId,
                                        userId,
                                        clientId,
                                        mode,
                                        attributes));
                    })
                    .replaceWithVoid();
        } else {
            throw new ServerSideBadRequestException("message body type mismatch, " +
                    message.getBody().getClass().getSimpleName());
        }
    }

    Uni<ClientModel> getClient(final Long clientId) {
        final var request = new GetClientRequest(clientId);
        return clientModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerAttributesModel> getPlayerAttributes(final Long userId, final Long playerId) {
        final var request = new GetPlayerAttributesRequest(userId, playerId);
        return userModule.getPlayerService().getPlayerAttributes(request)
                .map(GetPlayerAttributesResponse::getAttributes);
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
