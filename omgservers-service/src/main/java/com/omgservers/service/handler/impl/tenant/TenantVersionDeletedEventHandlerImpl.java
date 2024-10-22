package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionRequest;
import com.omgservers.schema.module.tenant.tenantVersion.GetTenantVersionResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantVersionDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.operation.DeleteTenantImagesByTenantVersionIdOperation;
import com.omgservers.service.handler.operation.DeleteTenantBuildRequestsByTenantVersionIdOperation;
import com.omgservers.service.handler.operation.DeleteTenantFilesArchivesByTenantVersionIdOperation;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantVersionDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final DeleteTenantBuildRequestsByTenantVersionIdOperation deleteTenantBuildRequestsByTenantVersionIdOperation;
    final DeleteTenantFilesArchivesByTenantVersionIdOperation deleteTenantFilesArchivesByTenantVersionIdOperation;
    final DeleteTenantImagesByTenantVersionIdOperation deleteTenantImagesByTenantVersionIdOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_VERSION_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantVersionDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantVersionId = body.getId();

        return getTenantVersion(tenantId, tenantVersionId)
                .flatMap(tenantVersion -> {
                    log.info("Deleted, {}", tenantVersion);

                    return deleteTenantFilesArchivesByTenantVersionIdOperation.execute(tenantId, tenantVersionId)
                            .flatMap(voidItem -> deleteTenantBuildRequestsByTenantVersionIdOperation.execute(tenantId,
                                    tenantVersionId))
                            .flatMap(voidItem -> deleteTenantImagesByTenantVersionIdOperation.execute(tenantId,
                                    tenantVersionId));
                })
                .replaceWithVoid();
    }

    Uni<TenantVersionModel> getTenantVersion(final Long tenantId, final Long tenantVersionId) {
        final var request = new GetTenantVersionRequest(tenantId, tenantVersionId);
        return tenantModule.getService().getTenantVersion(request)
                .map(GetTenantVersionResponse::getTenantVersion);
    }
}
