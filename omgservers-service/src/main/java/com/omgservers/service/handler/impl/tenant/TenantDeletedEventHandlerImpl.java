package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.DeleteTenantAliasesOperation;
import com.omgservers.service.operation.entity.DeleteEntityOperation;
import com.omgservers.service.operation.task.DeleteTaskOperation;
import com.omgservers.service.operation.tenant.DeleteTenantPermissionsOperation;
import com.omgservers.service.operation.tenant.DeleteTenantProjectsOperation;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeletedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    final DeleteTenantPermissionsOperation deleteTenantPermissionsOperation;
    final DeleteTenantProjectsOperation deleteTenantProjectsOperation;
    final DeleteTenantAliasesOperation deleteTenantAliasesOperation;
    final DeleteTaskOperation deleteTaskOperation;
    final DeleteEntityOperation deleteEntityOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return getTenant(tenantId)
                .flatMap(tenant -> {
                    log.debug("Deleted, {}", tenant);

                    return deleteTenantPermissionsOperation.execute(tenantId)
                            .flatMap(voidItem -> deleteTenantProjectsOperation.execute(tenantId))
                            .flatMap(voidItem -> deleteEntityOperation.executeFailSafe(tenantId))
                            .flatMap(voidItem -> deleteTaskOperation.execute(tenantId, tenantId))
                            .flatMap(voidItem -> deleteTenantAliasesOperation.execute(tenantId));
                })
                .replaceWithVoid();
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantShard.getService().execute(request)
                .map(GetTenantResponse::getTenant);
    }
}
