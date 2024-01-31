package com.omgservers.service.module.player.impl.service.playerService.impl.method.createClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.ClientModelFactory;
import com.omgservers.service.factory.PlayerModelFactory;
import com.omgservers.service.factory.RuntimeClientModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
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

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final TenantModule tenantModule;
    final UserModule userModule;

    final RuntimeClientModelFactory runtimeClientModelFactory;
    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateClientPlayerResponse> createClient(final CreateClientPlayerRequest request) {
        log.debug("Create client, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute("userId");

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var secret = request.getSecret();

        return tenantModule.getShortcutService().validateStageSecret(tenantId, stageId, secret)
                .flatMap(rawToken -> findOrCreatePlayer(userId, tenantId, stageId)
                        .flatMap(player -> {
                            final var playerId = player.getId();
                            return createClient(userId, playerId, tenantId, stageId)
                                    .flatMap(client -> clientModule.getShortcutService().syncClient(client)
                                            .replaceWith(client.getId()));
                        })
                )
                .map(CreateClientPlayerResponse::new);
    }

    Uni<PlayerModel> findOrCreatePlayer(final Long userId,
                                        final Long tenantId,
                                        final Long stageId) {
        return userModule.getShortcutService().findPlayer(userId, stageId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createPlayer(userId, tenantId, stageId));
    }

    Uni<PlayerModel> createPlayer(final Long userId,
                                  final Long tenantId,
                                  final Long stageId) {
        final var player = playerModelFactory.create(userId, tenantId, stageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userModule.getPlayerService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final Long tenantId,
                                  final Long stageId) {
        return tenantModule
                .getShortcutService()
                .selectLatestStageVersion(tenantId, stageId)
                .flatMap(version -> {
                    final var versionId = version.getId();
                    return tenantModule
                            .getShortcutService()
                            .selectVersionMatchmaker(tenantId, versionId)
                            .map(versionMatchmaker -> {
                                final var matchmakerId = versionMatchmaker.getMatchmakerId();
                                final var client = clientModelFactory.create(userId,
                                        playerId,
                                        tenantId,
                                        versionId,
                                        matchmakerId);
                                return client;
                            });
                });
    }

}
