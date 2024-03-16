package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.MatchmakerRequestConfigModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.MatchmakerRequestModelFactory;
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

    final MatchmakerRequestModelFactory matchmakerRequestModelFactory;

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
                                        attributes,
                                        event.getIdempotencyKey()));
                    })
                    .replaceWithVoid();
        } else {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.MATCHMAKER_MESSAGE_BODY_TYPE_MISMATCH,
                    "body type mismatch, " + message.getBody().getClass().getSimpleName());
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
                                       final PlayerAttributesModel attributes,
                                       final String idempotencyKey) {
        final var requestConfig = new MatchmakerRequestConfigModel(attributes);
        final var requestModel = matchmakerRequestModelFactory.create(matchmakerId,
                userId,
                clientId,
                mode,
                requestConfig,
                idempotencyKey);
        final var request = new SyncMatchmakerRequestRequest(requestModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerRequest(request)
                .map(SyncMatchmakerRequestResponse::getCreated)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION)) {
                            log.warn("Idempotency was violated, object={}, {}", requestModel, t.getMessage());
                            return Uni.createFrom().item(Boolean.FALSE);
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }
}
