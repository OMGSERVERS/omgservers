package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.module.client.SyncClientRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreateClientMethodImpl implements CreateClientMethod {

    final ClientShard clientShard;
    final TenantShard tenantShard;
    final UserShard userShard;

    final GetIdByProjectOperation getIdByProjectOperation;
    final GetIdByTenantOperation getIdByTenantOperation;
    final GetIdByStageOperation getIdByStageOperation;

    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateClientPlayerResponse> execute(final CreateClientPlayerRequest request) {
        log.trace("{}", request);

        final var userId = securityIdentity
                .<Long>getAttribute(SecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenant = request.getTenant();
        final var project = request.getProject();
        final var stage = request.getStage();

        return getIdByTenantOperation.execute(tenant)
                .flatMap(tenantId -> getIdByProjectOperation.execute(tenantId, project)
                        .flatMap(tenantProjectId -> getIdByStageOperation.execute(tenantId, tenantProjectId, stage)
                                .flatMap(tenantStageId -> findOrCreatePlayer(userId, tenantId, tenantStageId)
                                        .flatMap(player -> {
                                            final var playerId = player.getId();
                                            return createClient(userId, playerId, tenantId, tenantStageId)
                                                    .map(client -> {
                                                        final var clientId = client.getId();
                                                        log.info("The new client \"{}\" was created",
                                                                clientId);
                                                        return clientId;
                                                    });
                                        }))
                        )
                )
                .map(CreateClientPlayerResponse::new);
    }

    Uni<PlayerModel> findOrCreatePlayer(final Long userId,
                                        final Long tenantId,
                                        final Long tenantStageId) {
        return findPlayer(userId, tenantStageId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createPlayer(userId, tenantId, tenantStageId));
    }

    Uni<PlayerModel> findPlayer(final Long userId, final Long tenantStageId) {
        final var request = new FindPlayerRequest(userId, tenantStageId);
        return userShard.getService().findPlayer(request)
                .map(FindPlayerResponse::getPlayer);
    }

    Uni<PlayerModel> createPlayer(final Long userId,
                                  final Long tenantId,
                                  final Long tenantStageId) {
        final var player = playerModelFactory.create(userId, tenantId, tenantStageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userShard.getService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final Long tenantId,
                                  final Long tenantStageId) {
        return selectTenantDeployment(tenantId, tenantStageId)
                .flatMap(tenantDeployment -> {
                    final var tenantDeploymentId = tenantDeployment.getId();
                    final var client = clientModelFactory.create(userId,
                            playerId,
                            tenantId,
                            tenantDeploymentId);

                    final var request = new SyncClientRequest(client);
                    return clientShard.getService().syncClient(request)
                            .replaceWith(client);
                });
    }

    Uni<TenantDeploymentModel> selectTenantDeployment(final Long tenantId, final Long tenantStageId) {
        final var request = new SelectTenantDeploymentRequest(tenantId,
                tenantStageId,
                SelectTenantDeploymentRequest.StrategyEnum.LATEST);
        return tenantShard.getService().selectTenantDeployment(request)
                .map(SelectTenantDeploymentResponse::getTenantDeployment);
    }
}
