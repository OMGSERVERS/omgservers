package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefQualifierEnum;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.SyncRootEntityRefResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantCreatedEventBodyModel;
import com.omgservers.service.factory.root.RootEntityRefModelFactory;
import com.omgservers.service.factory.system.JobModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.service.job.dto.SyncJobRequest;
import com.omgservers.service.service.job.dto.SyncJobResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final AliasModule aliasModule;
    final RootModule rootModule;

    final JobService jobService;

    final GetConfigOperation getConfigOperation;

    final RootEntityRefModelFactory rootEntityRefModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return getTenant(tenantId)
                .flatMap(tenant -> {
                    log.debug("Created, {}", tenant);

                    final var idempotencyKey = event.getId().toString();

                    return syncRootEntityRef(tenantId, idempotencyKey)
                            .flatMap(created -> syncTenantJob(tenantId, idempotencyKey));
                })
                .replaceWithVoid();
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantModule.getService().getTenant(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<Boolean> syncRootEntityRef(final Long tenantId,
                                   final String idempotencyKey) {
        return findRootEntityAlias()
                .flatMap(alias -> {
                    final var rootId = alias.getEntityId();

                    final var rootEntityRef = rootEntityRefModelFactory.create(idempotencyKey, rootId,
                            RootEntityRefQualifierEnum.TENANT,
                            tenantId);
                    final var request = new SyncRootEntityRefRequest(rootEntityRef);
                    return rootModule.getService().syncRootEntityRefWithIdempotency(request)
                            .map(SyncRootEntityRefResponse::getCreated);
                });
    }

    Uni<AliasModel> findRootEntityAlias() {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<Boolean> syncTenantJob(final Long tenantId,
                               final String idempotencyKey) {
        final var job = jobModelFactory.create(JobQualifierEnum.TENANT,
                tenantId,
                tenantId,
                idempotencyKey);

        final var syncEventRequest = new SyncJobRequest(job);
        return jobService.syncJobWithIdempotency(syncEventRequest)
                .map(SyncJobResponse::getCreated);
    }
}
