package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.matchmaker.SyncRequestResponse;
import com.omgservers.dto.user.GetClientRequest;
import com.omgservers.dto.user.GetClientResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.client.ClientModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.RequestModelFactory;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerRequestedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GenerateIdOperation generateIdOperation;

    final RequestModelFactory requestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.MATCHMAKER_REQUESTED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (MatchmakerRequestedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var stageId = body.getStageId();
        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();
        final var mode = body.getMode();

        return getClient(userId, clientId)
                .flatMap(client -> {
                    final var matchmakerId = client.getDefaultMatchmakerId();
                    log.info("Matchmaker was requested, " +
                                    "mode={}, " +
                                    "matchmakerId={}, " +
                                    "userId={}, " +
                                    "clientId={}, " +
                                    "tenantId={}, " +
                                    "stageId={}",
                            mode,
                            matchmakerId,
                            userId,
                            clientId,
                            tenantId,
                            stageId);

                    return getPlayerAttributes(userId, playerId)
                            .flatMap(attributes -> syncMatchmakerRequest(matchmakerId,
                                    userId,
                                    clientId,
                                    mode,
                                    attributes));
                });
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var getClientServiceRequest = new GetClientRequest(userId, clientId);
        return userModule.getClientService().getClient(getClientServiceRequest)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerAttributesModel> getPlayerAttributes(Long userId, Long playerId) {
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
