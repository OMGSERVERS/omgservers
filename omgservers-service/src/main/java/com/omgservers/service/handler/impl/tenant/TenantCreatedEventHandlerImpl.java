package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.entity.EntityQualifierEnum;
import com.omgservers.schema.model.task.TaskQualifierEnum;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.entity.CreateEntityOperation;
import com.omgservers.service.operation.task.CreateTaskOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    final CreateEntityOperation createEntityOperation;
    final CreateTaskOperation createTaskOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getTenant(tenantId)
                .flatMap(tenant -> {
                    log.debug("Created, {}", tenant);

                    return createEntityOperation.execute(EntityQualifierEnum.TENANT, tenantId, idempotencyKey)
                            .flatMap(created -> createTaskOperation.execute(TaskQualifierEnum.TENANT,
                                    tenantId,
                                    idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantShard.getService().execute(request)
                .map(GetTenantResponse::getTenant);
    }
}
