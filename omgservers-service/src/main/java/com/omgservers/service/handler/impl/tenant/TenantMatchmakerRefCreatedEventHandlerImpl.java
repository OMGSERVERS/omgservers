package com.omgservers.service.handler.impl.tenant;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.GetTenantMatchmakerRefResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.FindTenantMatchmakerRequestResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantMatchmakerRefCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_MATCHMAKER_REF_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantMatchmakerRefCreatedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantMatchmakerRef(tenantId, id)
                .flatMap(tenantMatchmakerRef -> {
                    final var deploymentId = tenantMatchmakerRef.getDeploymentId();
                    final var matchmakerId = tenantMatchmakerRef.getMatchmakerId();
                    log.info("Tenant matchmaker ref was created, id={}, teanntDeploymentId={}/{}, matchmakerId={}",
                            tenantMatchmakerRef.getId(),
                            tenantId,
                            deploymentId,
                            matchmakerId);

                    return findAndDeleteTenantMatchmakerRequest(tenantId, deploymentId, matchmakerId);
                })
                .replaceWithVoid();
    }

    Uni<TenantMatchmakerRefModel> getTenantMatchmakerRef(final Long tenantId, final Long id) {
        final var request = new GetTenantMatchmakerRefRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantMatchmakerRef(request)
                .map(GetTenantMatchmakerRefResponse::getTenantMatchmakerRef);
    }

    Uni<Boolean> findAndDeleteTenantMatchmakerRequest(final Long tenantId,
                                                      final Long deploymentId,
                                                      final Long matchmakerId) {
        return findTenantMatchmakerRequest(tenantId, deploymentId, matchmakerId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(tenantMatchmakerRequest ->
                        deleteTenantMatchmakerRequest(tenantId, tenantMatchmakerRequest.getId()));
    }

    Uni<TenantMatchmakerRequestModel> findTenantMatchmakerRequest(final Long tenantId,
                                                                  final Long deploymentId,
                                                                  final Long matchmakerId) {
        final var request = new FindTenantMatchmakerRequestRequest(tenantId, deploymentId, matchmakerId);
        return tenantModule.getTenantService().findTenantMatchmakerRequest(request)
                .map(FindTenantMatchmakerRequestResponse::getTenantMatchmakerRequest);
    }

    Uni<Boolean> deleteTenantMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantMatchmakerRequest(request)
                .map(DeleteTenantMatchmakerRequestResponse::getDeleted);
    }
}
