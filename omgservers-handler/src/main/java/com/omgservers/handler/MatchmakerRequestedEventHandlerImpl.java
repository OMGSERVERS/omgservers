package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncRequestShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.user.GetPlayerAttributesShardedRequest;
import com.omgservers.dto.user.GetPlayerAttributesShardedResponse;
import com.omgservers.factory.RequestModelFactory;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.user.UserModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

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
                    return getPlayerAttributes(userId, playerId)
                            .flatMap(attributes -> {
                                final var requestConfig = new RequestConfigModel(attributes);
                                final var requestModel = requestModelFactory.create(
                                        matchmakerId,
                                        userId,
                                        clientId,
                                        mode,
                                        requestConfig);
                                final var request = new SyncRequestShardedRequest(requestModel);
                                return matchmakerModule.getMatchmakerShardedService().syncRequest(request);
                            });
                })
                .replaceWith(true);
    }

    Uni<StageModel> getStage(Long tenantId, Long stageId) {
        final var request = new GetStageShardedRequest(tenantId, stageId);
        return tenantModule.getStageShardedService().getStage(request)
                .map(GetStageShardedResponse::getStage);
    }

    Uni<Map<String, String>> getPlayerAttributes(Long userId, Long playerId) {
        final var request = new GetPlayerAttributesShardedRequest(userId, playerId);
        return userModule.getAttributeShardedService().getPlayerAttributes(request)
                .map(GetPlayerAttributesShardedResponse::getAttributes)
                .map(entities -> {
                    final var attributes = new HashMap<String, String>();
                    entities.forEach(entity -> attributes.put(entity.getName(), entity.getValue()));
                    return attributes;
                });
    }
}
