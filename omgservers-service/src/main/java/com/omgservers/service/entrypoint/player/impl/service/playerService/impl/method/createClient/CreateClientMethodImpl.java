package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createClient;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import com.omgservers.schema.module.client.SyncClientRequest;
import com.omgservers.schema.module.client.SyncClientResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsRequest;
import com.omgservers.schema.module.tenant.tenantVersion.ViewTenantVersionsResponse;
import com.omgservers.schema.module.user.FindPlayerRequest;
import com.omgservers.schema.module.user.FindPlayerResponse;
import com.omgservers.schema.module.user.SyncPlayerRequest;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.runtime.RuntimeAssignmentModelFactory;
import com.omgservers.service.factory.user.PlayerModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.security.ServiceSecurityAttributes;
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

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateClientPlayerResponse> createClient(final CreateClientPlayerRequest request) {
        log.debug("Create client, request={}", request);

        final var userId = securityIdentity.<Long>getAttribute(ServiceSecurityAttributes.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var secret = request.getStageSecret();

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

    Uni<TenantStageModel> validateStageSecret(final Long tenantId,
                                              final Long stageId,
                                              final String secret) {
        final var request = new GetTenantStageRequest(tenantId, stageId);
        return tenantModule.getTenantService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage)
                .invoke(tenantStage -> {
                    final var stageSecret = tenantStage.getSecret();
                    if (!stageSecret.equals(secret)) {
                        throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_STAGE_SECRET,
                                "stage secret is wrong");
                    }
                });
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
        return userModule.getUserService().findPlayer(request)
                .map(FindPlayerResponse::getPlayer);
    }

    Uni<PlayerModel> createPlayer(final Long userId,
                                  final Long tenantId,
                                  final Long stageId) {
        final var player = playerModelFactory.create(userId, tenantId, stageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userModule.getUserService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final Long tenantId,
                                  final Long stageId) {
        return selectStageVersionProjection(tenantId, stageId)
                .map(versionProjection -> {
                    final var versionId = versionProjection.getId();
                    final var client = clientModelFactory.create(userId,
                            playerId,
                            tenantId,
                            versionId);

                    return client;
                });
    }

    Uni<TenantVersionProjectionModel> selectStageVersionProjection(final Long tenantId, final Long stageId) {
        return viewVersionProjections(tenantId, stageId)
                .map(versions -> {
                    if (versions.isEmpty()) {
                        throw new ServerSideNotFoundException(
                                ExceptionQualifierEnum.VERSION_NOT_FOUND,
                                String.format("version was not selected, tenantId=%d, stageId=%d", tenantId, stageId));
                    } else {
                        return versions.stream()
                                .max(Comparator.comparing(TenantVersionProjectionModel::getId))
                                .get();
                    }
                });
    }

    Uni<List<TenantVersionProjectionModel>> viewVersionProjections(final Long tenantId, final Long stageId) {
        final var request = new ViewTenantVersionsRequest(tenantId, stageId);
        return tenantModule.getTenantService().viewTenantVersions(request)
                .map(ViewTenantVersionsResponse::getTenantVersionProjections);
    }

    Uni<Boolean> syncClient(ClientModel client) {
        final var request = new SyncClientRequest(client);
        return clientModule.getClientService().syncClient(request)
                .map(SyncClientResponse::getCreated);
    }
}
