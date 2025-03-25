package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.configuration.GlobalShardConfiguration;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.DeleteAliasesByEntityIdOperation;
import com.omgservers.service.operation.job.FindAndDeleteJobOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.operation.tenant.DeleteTenantPermissionsOperation;
import com.omgservers.service.operation.tenant.DeleteTenantProjectsOperation;
import com.omgservers.service.service.job.JobService;
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
public class TenantDeletedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;
    final AliasShard aliasShard;
    final RootShard rootShard;

    final JobService jobService;

    final DeleteTenantPermissionsOperation deleteTenantPermissionsOperation;
    final DeleteAliasesByEntityIdOperation deleteAliasesByEntityIdOperation;
    final DeleteTenantProjectsOperation deleteTenantProjectsOperation;
    final FindAndDeleteJobOperation findAndDeleteJobOperation;
    final GetServiceConfigOperation getServiceConfigOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getId();

        return getTenant(tenantId)
                .flatMap(tenant -> {
                    log.debug("Deleted, {}", tenant);

                    return deleteTenantPermissionsOperation.execute(tenantId)
                            .flatMap(voidItem -> deleteTenantProjectsOperation.execute(tenantId))
                            .flatMap(voidItem -> findAndDeleteRootTenantRef(tenantId))
                            .flatMap(voidItem -> findAndDeleteJobOperation.execute(tenantId, tenantId))
                            .flatMap(voidItem -> deleteAliasesByEntityIdOperation.execute(
                                    GlobalShardConfiguration.GLOBAL_SHARD_KEY, tenantId));
                })
                .replaceWithVoid();
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantShard.getService().execute(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<Void> findAndDeleteRootTenantRef(final Long tenantId) {
        return findRootEntityAlias()
                .flatMap(alias -> {
                    final var rootId = alias.getEntityId();
                    return findRootEntityRef(rootId, tenantId)
                            .onFailure(ServerSideNotFoundException.class)
                            .recoverWithNull()
                            .onItem().ifNotNull().transformToUni(rootEntityRef ->
                                    deleteRootEntityRef(rootId, rootEntityRef.getId()))
                            .replaceWithVoid();
                });
    }

    Uni<AliasModel> findRootEntityAlias() {
        final var request = new FindAliasRequest(GlobalShardConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                DefaultAliasConfiguration.ROOT_ENTITY_ALIAS);
        return aliasShard.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<RootEntityRefModel> findRootEntityRef(final Long rootId,
                                              final Long tenantId) {
        final var request = new FindRootEntityRefRequest(rootId, tenantId);
        return rootShard.getService().findRootEntityRef(request)
                .map(FindRootEntityRefResponse::getRootEntityRef);
    }

    Uni<Boolean> deleteRootEntityRef(final Long rootId, final Long id) {
        final var request = new DeleteRootEntityRefRequest(rootId, id);
        return rootShard.getService().deleteRootEntityRef(request)
                .map(DeleteRootEntityRefResponse::getDeleted);
    }
}
