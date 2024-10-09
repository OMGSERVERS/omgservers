package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.DeleteRootEntityRefResponse;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefRequest;
import com.omgservers.schema.module.root.rootEntityRef.FindRootEntityRefResponse;
import com.omgservers.schema.module.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.module.tenant.tenant.GetTenantResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.operation.DeleteTenantPermissionsOperation;
import com.omgservers.service.handler.operation.DeleteTenantProjectsOperation;
import com.omgservers.service.handler.operation.FindAndDeleteJobOperation;
import com.omgservers.service.module.root.RootModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import com.omgservers.service.service.job.JobService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final RootModule rootModule;

    final JobService jobService;

    final DeleteTenantPermissionsOperation deleteTenantPermissionsOperation;
    final DeleteTenantProjectsOperation deleteTenantProjectsOperation;
    final FindAndDeleteJobOperation findAndDeleteJobOperation;
    final GetConfigOperation getConfigOperation;

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
                    log.info("Tenant was deleted, tenant={}", tenantId);

                    return deleteTenantPermissionsOperation.execute(tenantId)
                            .flatMap(voidItem -> deleteTenantProjectsOperation.execute(tenantId))
                            .flatMap(voidItem -> findAndDeleteRootTenantRef(tenantId))
                            .flatMap(voidItem -> findAndDeleteJobOperation.execute(tenantId, tenantId));
                })
                .replaceWithVoid();
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantModule.getService().getTenant(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<Void> findAndDeleteRootTenantRef(final Long tenantId) {
        final var rootId = getConfigOperation.getServiceConfig().defaults().rootId();
        return findRootEntityRef(rootId, tenantId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(rootEntityRef ->
                        deleteRootEntityRef(rootId, rootEntityRef.getId()))
                .replaceWithVoid();
    }

    Uni<RootEntityRefModel> findRootEntityRef(final Long rootId,
                                              final Long tenantId) {
        final var request = new FindRootEntityRefRequest(rootId, tenantId);
        return rootModule.getService().findRootEntityRef(request)
                .map(FindRootEntityRefResponse::getRootEntityRef);
    }

    Uni<Boolean> deleteRootEntityRef(final Long rootId, final Long id) {
        final var request = new DeleteRootEntityRefRequest(rootId, id);
        return rootModule.getService().deleteRootEntityRef(request)
                .map(DeleteRootEntityRefResponse::getDeleted);
    }
}
