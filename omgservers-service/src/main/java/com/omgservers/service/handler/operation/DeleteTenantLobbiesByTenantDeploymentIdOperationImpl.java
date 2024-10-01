package com.omgservers.service.handler.operation;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.module.lobby.DeleteLobbyRequest;
import com.omgservers.schema.module.lobby.DeleteLobbyResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.module.lobby.LobbyModule;
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
class DeleteTenantLobbiesByTenantDeploymentIdOperationImpl implements DeleteTenantLobbiesByTenantDeploymentIdOperation {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public Uni<Void> execute(final Long tenantId, final Long tenantDeploymentId) {
        return viewTenantLobbyRefs(tenantId, tenantDeploymentId)
                .flatMap(tenantLobbyRefs -> Multi.createFrom().iterable(tenantLobbyRefs)
                        .onItem().transformToUniAndConcatenate(tenantLobbyRef ->
                                deleteLobby(tenantLobbyRef.getLobbyId())
                                        .onFailure(ServerSideClientException.class)
                                        .recoverWithItem(t -> {
                                            log.warn("Failed to delete lobby, " +
                                                            "tenantDeployment={}/{}, " +
                                                            "lobbyId={}" +
                                                            "{}:{}",
                                                    tenantId,
                                                    tenantDeploymentId,
                                                    tenantLobbyRef.getLobbyId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        }))
                        .collect().asList())
                .replaceWithVoid();
    }

    Uni<List<TenantLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long tenantDeploymentId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, tenantDeploymentId);
        return tenantModule.getTenantService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<Boolean> deleteLobby(final Long lobbyId) {
        final var request = new DeleteLobbyRequest(lobbyId);
        return lobbyModule.getLobbyService().deleteLobby(request)
                .map(DeleteLobbyResponse::getDeleted);
    }
}
