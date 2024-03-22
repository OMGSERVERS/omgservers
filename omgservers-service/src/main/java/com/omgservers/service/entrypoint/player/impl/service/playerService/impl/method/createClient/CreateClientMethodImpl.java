package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createClient;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.player.CreateClientPlayerRequest;
import com.omgservers.model.dto.player.CreateClientPlayerResponse;
import com.omgservers.model.dto.tenant.ValidateStageSecretRequest;
import com.omgservers.model.dto.tenant.ValidateStageSecretResponse;
import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import com.omgservers.model.dto.user.FindPlayerRequest;
import com.omgservers.model.dto.user.FindPlayerResponse;
import com.omgservers.model.dto.user.SyncPlayerRequest;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
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

import java.util.Comparator;
import java.util.List;

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

        return validateStageSecret(tenantId, stageId, secret)
                .flatMap(rawToken -> findOrCreatePlayer(userId, tenantId, stageId)
                        .flatMap(player -> {
                            final var playerId = player.getId();
                            return createClient(userId, playerId, tenantId, stageId)
                                    .flatMap(client -> syncClient(client)
                                            .replaceWith(client.getId()));
                        })
                )
                .map(CreateClientPlayerResponse::new);
    }

    Uni<StageModel> validateStageSecret(final Long tenantId,
                                        final Long stageId,
                                        final String secret) {
        final var validateStageSecretHelpRequest = new ValidateStageSecretRequest(tenantId, stageId, secret);
        return tenantModule.getStageService().validateStageSecret(validateStageSecretHelpRequest)
                .map(ValidateStageSecretResponse::getStage);
    }

    Uni<PlayerModel> findOrCreatePlayer(final Long userId,
                                        final Long tenantId,
                                        final Long stageId) {
        return findPlayer(userId, stageId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createPlayer(userId, tenantId, stageId));
    }

    Uni<PlayerModel> findPlayer(final Long userId, final Long stageId) {
        final var request = new FindPlayerRequest(userId, stageId);
        return userModule.getPlayerService().findPlayer(request)
                .map(FindPlayerResponse::getPlayer);
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
        return selectStageVersion(tenantId, stageId)
                .map(version -> {
                    final var versionId = version.getId();
                    final var client = clientModelFactory.create(userId,
                            playerId,
                            tenantId,
                            versionId);

                    return client;
                });
    }

    Uni<VersionModel> selectStageVersion(final Long tenantId, final Long stageId) {
        return viewVersions(tenantId, stageId)
                .map(versions -> {
                    if (versions.isEmpty()) {
                        throw new ServerSideNotFoundException(
                                ExceptionQualifierEnum.VERSION_NOT_FOUND,
                                String.format("version was not selected, tenantId=%d, stageId=%d", tenantId, stageId));
                    } else {
                        return versions.stream()
                                .max(Comparator.comparing(VersionModel::getId))
                                .get();
                    }
                });
    }

    Uni<List<VersionModel>> viewVersions(final Long tenantId, final Long stageId) {
        final var request = new ViewVersionsRequest(tenantId, stageId);
        return tenantModule.getVersionService().viewVersions(request)
                .map(ViewVersionsResponse::getVersions);
    }

    Uni<Boolean> syncClient(ClientModel client) {
        final var request = new SyncClientRequest(client);
        return clientModule.getClientService().syncClient(request)
                .map(SyncClientResponse::getCreated);
    }
}
