package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestRequest;
import com.omgservers.schema.module.tenant.tenantBuildRequest.GetTenantBuildRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantBuildRequestDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.operation.job.FindAndDeleteJobOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantBuildRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final FindAndDeleteJobOperation findAndDeleteJobOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_BUILD_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantBuildRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantBuildRequestId = body.getId();

        return getTenantBuildRequest(tenantId, tenantBuildRequestId)
                .flatMap(tenantBuildRequest -> {
                    log.debug("Deleted, {}", tenantBuildRequest);

                    return findAndDeleteJobOperation.execute(tenantId, tenantBuildRequestId);
                })
                .replaceWithVoid();
    }

    Uni<TenantBuildRequestModel> getTenantBuildRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantBuildRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantBuildRequest(request)
                .map(GetTenantBuildRequestResponse::getTenantBuildRequest);
    }
}
