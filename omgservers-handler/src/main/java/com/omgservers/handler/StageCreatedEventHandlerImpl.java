package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncMatchmakerRequest;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.module.system.SystemModule;
import com.omgservers.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.factory.MatchmakerModelFactory;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.tenant.factory.StagePermissionModelFactory;
import com.omgservers.module.user.UserModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.operation.getServers.GetServersOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageCreatedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final SystemModule systemModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final GetServersOperation getServersOperation;
    final GenerateIdOperation generateIdOperation;

    final StagePermissionModelFactory stagePermissionModelFactory;

    final MatchmakerModelFactory matchmakerModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.STAGE_CREATED;
    }

    @Override
    public Uni<Boolean> handle(EventModel event) {
        final var body = (StageCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();
        return getStage(tenantId, id)
                .flatMap(stage -> syncMatchmaker(stage.getMatchmakerId(), tenantId, stage.getId()))
                .replaceWith(true);
    }

    Uni<StageModel> getStage(Long tenantId, Long id) {
        final var request = new GetStageRequest(tenantId, id);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }

    Uni<MatchmakerModel> syncMatchmaker(final Long matchmakerId, final Long tenantId, final Long stageId) {
        final var matchmaker = matchmakerModelFactory.create(matchmakerId, tenantId, stageId);
        final var request = new SyncMatchmakerRequest(matchmaker);
        return matchmakerModule.getMatchmakerService().syncMatchmaker(request)
                .replaceWith(matchmaker);
    }
}
