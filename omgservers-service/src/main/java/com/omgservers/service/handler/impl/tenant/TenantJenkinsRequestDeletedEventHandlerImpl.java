package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.tenantJenkinsRequest.GetTenantJenkinsRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantJenkinsRequestDeletedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.handler.operation.FindAndDeleteJobOperation;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantJenkinsRequestDeletedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    final FindAndDeleteJobOperation findAndDeleteJobOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_JENKINS_REQUEST_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantJenkinsRequestDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var tenantJenkinsRequestId = body.getId();

        return getTenantJenkinsRequest(tenantId, tenantJenkinsRequestId)
                .flatMap(tenantJenkinsRequest -> {
                    final var jenkinsRequestVersionId = tenantJenkinsRequest.getVersionId();
                    final var qualifier = tenantJenkinsRequest.getQualifier();
                    log.info("Tenant jenkins request was deleted, " +
                                    "tenantJenkinsRequestId={}, tenantVersion={}/{}, qualifier={}",
                            tenantJenkinsRequest.getId(),
                            tenantId,
                            jenkinsRequestVersionId,
                            qualifier);

                    return findAndDeleteJobOperation.execute(tenantId, tenantJenkinsRequestId);
                })
                .replaceWithVoid();
    }

    Uni<TenantJenkinsRequestModel> getTenantJenkinsRequest(final Long tenantId, final Long id) {
        final var request = new GetTenantJenkinsRequestRequest(tenantId, id);
        return tenantModule.getService().getTenantJenkinsRequest(request)
                .map(GetTenantJenkinsRequestResponse::getTenantJenkinsRequest);
    }
}
