package com.omgservers.service.handler;

import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.StageCreatedEventBodyModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StageCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

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
                .flatMap(stage -> {
                    log.info("Stage was created, {}/{}", tenantId, id);
                    return Uni.createFrom().voidItem();
                })
                .replaceWith(true);
    }

    Uni<StageModel> getStage(final Long tenantId, final Long id) {
        final var request = new GetStageRequest(tenantId, id, false);
        return tenantModule.getStageService().getStage(request)
                .map(GetStageResponse::getStage);
    }
}
