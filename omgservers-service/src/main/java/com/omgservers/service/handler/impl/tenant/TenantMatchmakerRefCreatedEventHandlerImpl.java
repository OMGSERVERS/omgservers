package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.FindTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.FindTenantMatchmakerResourceResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantMatchmakerRefCreatedEventHandlerImpl implements EventHandler {

    final TenantShard tenantShard;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (TenantMatchmakerRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantMatchmakerRef(tenantId, id)
                .flatMap(tenantMatchmakerRef -> {
                    final var deploymentId = tenantMatchmakerRef.getDeploymentId();
                    final var matchmakerId = tenantMatchmakerRef.getMatchmakerId();
                    log.debug("Created, {}", tenantMatchmakerRef);

                    return findAndDeleteTenantMatchmakerResource(tenantId, deploymentId, matchmakerId);
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerRefModel> getTenantMatchmakerRef(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerRefRequest(tenantId, id);
        return tenantShard.getService().getTenantMatchmakerRef(request)
                .map(GetTenantMatchmakerRefResponse::getTenantMatchmakerRef);
    }

    Uni<Boolean> findAndDeleteTenantMatchmakerResource(final Long tenantId,
                                                       final Long deploymentId,
                                                       final Long matchmakerId) {
        return findTenantMatchmakerResource(tenantId, deploymentId, matchmakerId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(tenantMatchmakerResource ->
                        deleteTenantMatchmakerResource(tenantId, tenantMatchmakerResource.getId()));
    }

    Uni<TenantMatchmakerResourceModel> findTenantMatchmakerResource(final Long tenantId,
                                                                    final Long deploymentId,
                                                                    final Long matchmakerId) {
        final var request = new FindTenantMatchmakerResourceRequest(tenantId, deploymentId, matchmakerId);
        return tenantShard.getService().execute(request)
                .map(FindTenantMatchmakerResourceResponse::getTenantMatchmakerResource);
    }

    Uni<Boolean> deleteTenantMatchmakerResource(final Long tenantId, final Long id) {
        final var request = new DeleteTenantMatchmakerResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantMatchmakerResourceResponse::getDeleted);
    }
}
