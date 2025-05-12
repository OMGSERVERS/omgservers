package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefQualifierEnum;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.shard.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.factory.root.RootEntityRefModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.FindRootAliasOperation;
import com.omgservers.service.operation.job.CreateJobOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.root.RootShard;
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
    final AliasShard aliasShard;
    final RootShard rootShard;

    final GetServiceConfigOperation getServiceConfigOperation;
    final FindRootAliasOperation findRootAliasOperation;
    final CreateJobOperation createJobOperation;

    final RootEntityRefModelFactory rootEntityRefModelFactory;

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

                    return syncRootEntityRef(tenantId, idempotencyKey)
                            .flatMap(created -> createJobOperation.execute(JobQualifierEnum.TENANT,
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

    Uni<Boolean> syncRootEntityRef(final Long tenantId,
                                   final String idempotencyKey) {
        return findRootAliasOperation.execute()
                .flatMap(alias -> {
                    final var rootId = alias.getEntityId();

                    final var rootEntityRef = rootEntityRefModelFactory.create(idempotencyKey, rootId,
                            RootEntityRefQualifierEnum.TENANT,
                            tenantId);
                    final var request = new SyncRootEntityRefRequest(rootEntityRef);
                    return rootShard.getService().executeWithIdempotency(request)
                            .map(SyncRootEntityRefResponse::getCreated);
                });
    }
}
