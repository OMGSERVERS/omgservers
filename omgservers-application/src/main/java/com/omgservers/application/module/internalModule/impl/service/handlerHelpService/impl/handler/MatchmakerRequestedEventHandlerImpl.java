package com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.handler;

import com.omgservers.application.module.internalModule.impl.service.handlerHelpService.impl.EventHandler;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.body.MatchmakerRequestedEventBodyModel;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.CreateRequestInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.CreateRequestInternalResponse;
import com.omgservers.application.module.matchmakerModule.model.request.RequestConfigModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModelFactory;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.request.GetPlayerAttributesInternalRequest;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.response.GetPlayerAttributesInternalResponse;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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
                                final var requestConfig =
                                        new RequestConfigModel(userId, clientId, tenantId, stageId, mode, attributes);
                                final var requestModel = requestModelFactory.create(matchmakerId, requestConfig);
                                final var request = new CreateRequestInternalRequest(requestModel);
                                return matchmakerModule.getMatchmakerInternalService().createRequest(request)
                                        .map(CreateRequestInternalResponse::getRequest);
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
