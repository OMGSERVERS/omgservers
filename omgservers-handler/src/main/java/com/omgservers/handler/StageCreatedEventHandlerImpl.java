package com.omgservers.handler;

import com.omgservers.dto.matchmaker.SyncMatchmakerShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.factory.MatchmakerModelFactory;
import com.omgservers.factory.StagePermissionModelFactory;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.internal.impl.service.handlerService.impl.EventHandler;
import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.tenant.TenantModule;
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
    final InternalModule internalModule;
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
                .flatMap(stage -> syncMatchmaker(tenantId, stage.getId()))
                .replaceWith(true);
    }

    Uni<StageModel> getStage(Long tenantId, Long id) {
        final var request = new GetStageShardedRequest(tenantId, id);
        return tenantModule.getStageShardedService().getStage(request)
                .map(GetStageShardedResponse::getStage);
    }

    Uni<MatchmakerModel> syncMatchmaker(final Long tenantId, final Long stageId) {
        final var matchmaker = matchmakerModelFactory.create(tenantId, stageId);
        final var request = new SyncMatchmakerShardedRequest(matchmaker);
        return matchmakerModule.getMatchmakerShardedService().syncMatchmaker(request)
                .replaceWith(matchmaker);
    }
}
