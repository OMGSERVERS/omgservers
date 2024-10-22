package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantStageDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.operation.DeleteTenantStagePermissionsOperation;
import com.omgservers.service.handler.operation.DeleteTenantDeploymentsByTenantStageIdOperation;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantStageDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;

    final DeleteTenantDeploymentsByTenantStageIdOperation deleteTenantDeploymentsByTenantStageIdOperation;
    final DeleteTenantStagePermissionsOperation deleteTenantStagePermissionsOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_STAGE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantStageDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantStageId = body.getId();

        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> {
                    log.info("Deleted, {}", tenantStage);

                    return deleteTenantStagePermissionsOperation.execute(tenantId, tenantStageId)
                            .flatMap(voidItem -> deleteTenantDeploymentsByTenantStageIdOperation
                                    .execute(tenantId, tenantStageId));
                })
                .replaceWithVoid();
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantModule.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }
}
