package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerRequestResponse;
import com.omgservers.model.dto.user.GetPlayerAttributesRequest;
import com.omgservers.model.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.player.MatchmakerMessageReceivedEventBodyModel;
import com.omgservers.model.message.body.MatchmakerMessageBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.MatchmakerRequestConfigModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
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

import java.util.Comparator;
import java.util.List;

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
            final var mode = messageBody.getMode();

            log.info("Matchmaker was requested, clientId={}, mode={}", clientId, mode);

            return selectClientMatchmakerRef(clientId)
                    .flatMap(clientMatchmakerRef -> {
                        final var matchmakerId = clientMatchmakerRef.getMatchmakerId();

                        return getClient(clientId)
                                .flatMap(client -> {
                                    final var userId = client.getUserId();
                                    final var playerId = client.getPlayerId();

                                    return getPlayerAttributes(userId, playerId)
                                            .flatMap(attributes -> syncMatchmakerRequest(matchmakerId,
                                                    userId,
                                                    clientId,
                                                    mode,
                                                    attributes,
                                                    event.getIdempotencyKey()));
                                });
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

    Uni<ClientMatchmakerRefModel> selectClientMatchmakerRef(final Long clientId) {
        return viewClientMatchmakerRefs(clientId)
                .map(clientMatchmakerRefs -> {
                    if (clientMatchmakerRefs.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                String.format("matchmaker was not selected, clientId=%d", clientId));
                    } else {
                        return clientMatchmakerRefs.stream()
                                .max(Comparator.comparing(ClientMatchmakerRefModel::getId))
                                .get();
                    }
                });
    }

    Uni<List<ClientMatchmakerRefModel>> viewClientMatchmakerRefs(final Long clientId) {
        final var request = new ViewClientMatchmakerRefsRequest(clientId);
        return clientModule.getClientService().viewClientMatchmakerRefs(request)
                .map(ViewClientMatchmakerRefsResponse::getClientMatchmakerRefs);
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
