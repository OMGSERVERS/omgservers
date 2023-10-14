package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncRequestRequest;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.user.GetPlayerAttributesRequest;
import com.omgservers.dto.user.GetPlayerAttributesResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.model.stage.StageModel;
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

        return getStage(tenantId, stageId)
                .flatMap(stage -> {
                    final var matchmakerId = stage.getMatchmakerId();

                    log.info("Matchmaker was requested, " +
                                    "matchmakerId={}, clientId={}, mode={}, userId={}, tenantId={}, stageId={}",
                            matchmakerId, clientId, mode, userId, tenantId, stageId);

                    return getPlayerAttributes(userId, playerId)
                            .flatMap(attributes -> {
                                final var requestConfig = new RequestConfigModel(attributes);
                                final var requestModel = requestModelFactory.create(
                                        matchmakerId,
                                        userId,
                                        clientId,
                                        mode,
                                        requestConfig);
                                final var request = new SyncRequestRequest(requestModel);
                                return matchmakerModule.getMatchmakerService().syncRequest(request);
                            });
                })
                .replaceWith(true);
    }

    Uni<StageModel> getStage(Long tenantId, Long stageId) {
        final var request = new GetStageRequest(tenantId, stageId);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<PlayerAttributesModel> getPlayerAttributes(Long userId, Long playerId) {
        final var request = new GetPlayerAttributesRequest(userId, playerId);
        return userModule.getPlayerService().getPlayerAttributes(request)
                .map(GetPlayerAttributesResponse::getAttributes);
    }
}
