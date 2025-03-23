package com.omgservers.service.operation.assignment;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceStatusEnum;
import com.omgservers.schema.module.lobby.GetLobbyRequest;
import com.omgservers.schema.module.lobby.GetLobbyResponse;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.ViewTenantLobbyResourcesResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.tenant.TenantShard;
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

    final TenantShard tenantShard;
    final LobbyShard lobbyShard;

    @Override
    public Uni<LobbyModel> execute(final Long tenantId,
                                   final Long tenantDeploymentId) {
        return selectRandomTenantLobbyResource(tenantId, tenantDeploymentId)
                .flatMap(tenantLobbyRef -> {
                    final var lobbyId = tenantLobbyRef.getLobbyId();
                    return getLobby(lobbyId);
                });
    }

    Uni<TenantLobbyResourceModel> selectRandomTenantLobbyResource(final Long tenantId,
                                                                  final Long tenantDeploymentId) {
        return viewCreatedTenantLobbyResources(tenantId, tenantDeploymentId)
                .map(tenantLobbyResources -> {
                    if (tenantLobbyResources.isEmpty()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.LOBBY_NOT_FOUND,
                                String.format("lobby was not selected, tenantDeployment=%d/%d", tenantId,
                                        tenantDeploymentId));
                    } else {
                        final var randomIndex = ThreadLocalRandom.current()
                                .nextInt(tenantLobbyResources.size()) % tenantLobbyResources.size();
                        final var randomTenantLobbyResource = tenantLobbyResources
                                .get(randomIndex);
                        return randomTenantLobbyResource;
                    }
                });
    }

    Uni<List<TenantLobbyResourceModel>> viewCreatedTenantLobbyResources(final Long tenantId, final Long deploymentId) {
        final var request = new ViewTenantLobbyResourcesRequest(tenantId,
                deploymentId,
                TenantLobbyResourceStatusEnum.CREATED);
        return tenantShard.getService().execute(request)
                .map(ViewTenantLobbyResourcesResponse::getTenantLobbyResources);
    }


    Uni<LobbyModel> getLobby(final Long lobbyId) {
        final var request = new GetLobbyRequest(lobbyId);
        return lobbyShard.getService().getLobby(request)
                .map(GetLobbyResponse::getLobby);
    }
}
