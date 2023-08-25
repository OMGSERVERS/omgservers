package com.omgservers.application.module.internalModule2.handler;

import com.omgservers.base.factory.RequestModelFactory;
import com.omgservers.base.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.base.impl.operation.generateIdOperation.GenerateIdOperation;
import com.omgservers.dto.matchmakerModule.SyncRequestInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalRequest;
import com.omgservers.dto.userModule.GetPlayerAttributesInternalResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.model.request.RequestConfigModel;
import com.omgservers.model.stage.StageModel;
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
                                final var requestConfig = new RequestConfigModel(mode, attributes);
                                final var requestModel = requestModelFactory.create(matchmakerId, userId, clientId, requestConfig);
                                final var request = new SyncRequestInternalRequest(requestModel);
                                return matchmakerModule.getMatchmakerInternalService().syncRequest(request);
                            });
                })
                .replaceWith(true);
    }

    Uni<StageModel> getStage(Long tenantId, Long stageId) {
        final var request = new GetStageInternalRequest(tenantId, stageId);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage);
    }

    Uni<Map<String, String>> getPlayerAttributes(Long userId, Long playerId) {
        final var request = new GetPlayerAttributesInternalRequest(userId, playerId);
        return userModule.getAttributeInternalService().getPlayerAttributes(request)
                .map(GetPlayerAttributesInternalResponse::getAttributes)
                .map(entities -> {
                    final var attributes = new HashMap<String, String>();
                    entities.forEach(entity -> attributes.put(entity.getName(), entity.getValue()));
                    return attributes;
                });
    }
}
