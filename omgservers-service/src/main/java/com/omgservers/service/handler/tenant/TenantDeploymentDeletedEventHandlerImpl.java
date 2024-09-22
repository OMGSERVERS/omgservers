package com.omgservers.service.handler.tenant;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerRequest;
import com.omgservers.schema.module.matchmaker.DeleteMatchmakerResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.DeleteTenantLobbyRequestResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.ViewTenantLobbyRequestsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRef.ViewTenantMatchmakerRefsResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.DeleteTenantMatchmakerRequestResponse;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerRequest.ViewTenantMatchmakerRequestsResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.tenant.TenantDeploymentDeletedEventBodyModel;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class TenantDeploymentDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    final TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;
    final TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.TENANT_DEPLOYMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (TenantDeploymentDeletedEventBodyModel) event.getBody();
        final var tenantId = body.getTenantId();
        final var id = body.getId();

        return getTenantDeployment(tenantId, id)
                .flatMap(tenantDeployment -> {
                    log.info("Tenant deployment was deleted, tenantDeployment={}/{}, " +
                                    "tenantVersionId={}, " +
                                    "tenantStageId={}",
                            tenantId,
                            id,
                            tenantDeployment.getVersionId(),
                            tenantDeployment.getStageId());

                    return deleteTenantLobbyRequests(tenantId, id)
                            .flatMap(voidItem -> deleteTenantLobbies(tenantId, id))
                            .flatMap(voidItem -> deleteTenantMatchmakerRequests(tenantId, id))
                            .flatMap(voidItem -> deleteTenantMatchmakers(tenantId, id));
                })
                .replaceWithVoid();
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getTenantService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Void> deleteTenantLobbyRequests(final Long tenantId, final Long deploymentId) {
        return viewTenantLobbyRequests(tenantId, deploymentId)
                .flatMap(tenantLobbyRequests -> Multi.createFrom().iterable(tenantLobbyRequests)
                        .onItem().transformToUniAndConcatenate(tenantLobbyRequest ->
                                deleteTenantLobbyRequest(tenantId, tenantLobbyRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRequestModel>> viewTenantLobbyRequests(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantLobbyRequestsRequest(tenantId, deploymentId);
        return tenantModule.getTenantService().viewTenantLobbyRequests(request)
                .map(ViewTenantLobbyRequestsResponse::getTenantLobbyRequests);
    }

    Uni<Boolean> deleteTenantLobbyRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantLobbyRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantLobbyRequest(request)
                .map(DeleteTenantLobbyRequestResponse::getDeleted);
    }

    Uni<Void> deleteTenantLobbies(final Long tenantId, final Long deploymentId) {
        return viewTenantLobbyRefs(tenantId, deploymentId)
                .flatMap(tenantLobbyRefs -> Multi.createFrom().iterable(tenantLobbyRefs)
                        .onItem().transformToUniAndConcatenate(tenantLobbyRef ->
                                deleteLobby(tenantLobbyRef.getLobbyId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, deploymentId);
        return tenantModule.getTenantService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<Boolean> deleteLobby(final Long lobbyId) {
        final var request = new DeleteLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().deleteLobby(request)
                .map(DeleteLobbyResponse::getDeleted);
    }

    Uni<Void> deleteTenantMatchmakerRequests(final Long tenantId, final Long deploymentId) {
        return viewTenantMatchmakerRequests(tenantId, deploymentId)
                .flatMap(tenantMatchmakerRequests -> Multi.createFrom().iterable(tenantMatchmakerRequests)
                        .onItem().transformToUniAndConcatenate(tenantMatchmakerRequest ->
                                deleteTenantMatchmakerRequest(tenantId, tenantMatchmakerRequest.getId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantMatchmakerRequestModel>> viewTenantMatchmakerRequests(final Long tenantId,
                                                                         final Long deploymentId) {
        final var request = new ViewTenantMatchmakerRequestsRequest(tenantId, deploymentId);
        return tenantModule.getTenantService().viewTenantMatchmakerRequests(request)
                .map(ViewTenantMatchmakerRequestsResponse::getTenantMatchmakerRequests);
    }

    Uni<Boolean> deleteTenantMatchmakerRequest(final Long tenantId, final Long id) {
        final var request = new DeleteTenantMatchmakerRequestRequest(tenantId, id);
        return tenantModule.getTenantService().deleteTenantMatchmakerRequest(request)
                .map(DeleteTenantMatchmakerRequestResponse::getDeleted);
    }

    Uni<Void> deleteTenantMatchmakers(final Long tenantId, final Long deploymentId) {
        return viewTenantMatchmakerRefs(tenantId, deploymentId)
                .flatMap(tenantMatchmakerRefs -> Multi.createFrom().iterable(tenantMatchmakerRefs)
                        .onItem().transformToUniAndConcatenate(tenantMatchmakerRef ->
                                deleteMatchmaker(tenantMatchmakerRef.getMatchmakerId()))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantMatchmakerRefModel>> viewTenantMatchmakerRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantMatchmakerRefsRequest(tenantId, deploymentId);
        return tenantModule.getTenantService().viewTenantMatchmakerRefs(request)
                .map(ViewTenantMatchmakerRefsResponse::getTenantMatchmakerRefs);
    }

    Uni<Boolean> deleteMatchmaker(final Long matchmakerId) {
        final var request = new DeleteMatchmakerRequest(matchmakerId);
        return matchmakerModule.getMatchmakerService().deleteMatchmaker(request)
                .map(DeleteMatchmakerResponse::getDeleted);
    }
}
