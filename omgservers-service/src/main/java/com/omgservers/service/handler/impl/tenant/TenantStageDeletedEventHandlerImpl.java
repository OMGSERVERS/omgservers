package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantStageDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.DeleteTenantStageAliasesOperation;
import com.omgservers.service.operation.task.DeleteTaskOperation;
import com.omgservers.service.operation.tenant.DeleteTenantStageDeploymentResourcesOperation;
import com.omgservers.service.operation.tenant.DeleteTenantStagePermissionsOperation;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantStageDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final TenantShard tenantShard;

    final DeleteTenantStageDeploymentResourcesOperation deleteTenantStageDeploymentResourcesOperation;
    final DeleteTenantStagePermissionsOperation deleteTenantStagePermissionsOperation;
    final DeleteTenantStageAliasesOperation deleteTenantStageAliasesOperation;
    final DeleteTaskOperation deleteTaskOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_STAGE_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantStageDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantStageId = body.getId();

        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> {
                    log.debug("Deleted, {}", tenantStage);

                    return deleteTenantStagePermissionsOperation.execute(tenantId, tenantStageId)
                            .flatMap(voidItem -> deleteTenantStageDeploymentResourcesOperation
                                    .execute(tenantId, tenantStageId))
                            .flatMap(voidItem -> deleteTaskOperation
                                    .execute(tenantId, tenantStageId))
                            .flatMap(voidItem -> deleteTenantStageAliasesOperation
                                    .execute(tenantId, tenantStageId));
                })
                .replaceWithVoid();
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }
}
