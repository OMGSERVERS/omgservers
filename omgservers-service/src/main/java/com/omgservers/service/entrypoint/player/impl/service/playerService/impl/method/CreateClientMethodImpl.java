package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.schema.module.client.client.SyncClientRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.operation.alias.GetIdByProjectOperation;
import com.omgservers.service.operation.alias.GetIdByStageOperation;
import com.omgservers.service.operation.alias.GetIdByTenantOperation;
import com.omgservers.service.security.SecurityAttributesEnum;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
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
                                                        log.info("New client \"{}\" created", clientId);
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
        return userShard.getService().execute(request)
                .map(FindPlayerResponse::getPlayer);
    }

    Uni<PlayerModel> createPlayer(final Long userId,
                                  final Long tenantId,
                                  final Long tenantStageId) {
        final var player = playerModelFactory.create(userId, tenantId, tenantStageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userShard.getService().execute(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final Long tenantId,
                                  final Long tenantStageId) {
        return selectTenantDeploymentResource(tenantId, tenantStageId)
                .flatMap(tenantDeploymentResource -> {
                    final var deploymentId = tenantDeploymentResource.getDeploymentId();
                    final var client = clientModelFactory.create(userId,
                            playerId,
                            deploymentId);

                    final var request = new SyncClientRequest(client);
                    return clientShard.getService().execute(request)
                            .replaceWith(client);
                });
    }

    Uni<TenantDeploymentResourceModel> selectTenantDeploymentResource(final Long tenantId,
                                                                      final Long tenantStageId) {
        final var request = new ViewTenantDeploymentResourcesRequest(tenantId, tenantStageId,
                TenantDeploymentResourceStatusEnum.CREATED);
        return tenantShard.getService().execute(request)
                .map(ViewTenantDeploymentResourcesResponse::getTenantDeploymentResources)
                .map(tenantDeploymentResources -> {
                    if (tenantDeploymentResources.isEmpty()) {
                        throw new ServerSideConflictException(ExceptionQualifierEnum.DEPLOYMENT_NOT_FOUND,
                                "Deployment not found");
                    } else {
                        return tenantDeploymentResources.getLast();
                    }
                });
    }
}
