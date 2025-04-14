package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantStageCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.job.CreateJobOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantStageCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    final CreateJobOperation createJobOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_STAGE_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantStageCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantStageId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> {
                    log.debug("Created, {}", tenantStage);

                    return createJobOperation.execute(JobQualifierEnum.STAGE,
                            tenantId,
                            tenantStageId,
                            idempotencyKey);
                })
                .replaceWithVoid();
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }
}
