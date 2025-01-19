package com.omgservers.service.operation.selectRandomLobby;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.ViewTenantLobbyRefsResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.lobby.LobbyModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRandomLobbyOperationImpl implements SelectRandomLobbyOperation {

    final TenantModule tenantModule;
    final LobbyModule lobbyModule;

    @Override
    public Uni<LobbyModel> execute(final Long tenantId,
                                   final Long tenantDeploymentId) {
        return selectTenantLobbyRef(tenantId, tenantDeploymentId)
                .flatMap(tenantLobbyRef -> {
                    final var lobbyId = tenantLobbyRef.getLobbyId();
                    return getLobby(lobbyId);
                });
    }

    Uni<TenantLobbyRefModel> selectTenantLobbyRef(final Long tenantId, final Long tenantDeploymentId) {
        return viewTenantLobbyRefs(tenantId, tenantDeploymentId)
                .map(refs -> {
                    if (refs.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.LOBBY_NOT_FOUND,
                                String.format("lobby was not selected, tenantDeployment=%d/%d", tenantId,
                                        tenantDeploymentId));
                    } else {
                        final var randomIndex = ThreadLocalRandom.current().nextInt(refs.size()) % refs.size();
                        final var randomTenantLobbyRef = refs.get(randomIndex);
                        return randomTenantLobbyRef;
                    }
                });
    }

    Uni<List<TenantLobbyRefModel>> viewTenantLobbyRefs(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantLobbyRefsRequest(tenantId, deploymentId);
        return tenantModule.getService().viewTenantLobbyRefs(request)
                .map(ViewTenantLobbyRefsResponse::getTenantLobbyRefs);
    }

    Uni<LobbyModel> getLobby(final Long lobbyId) {
        final var request = new GetLobbyRequest(lobbyId);
        return lobbyModule.getService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }
}
