package com.omgservers.service.entrypoint.player.impl.service.playerService.impl.method.createClient;

import com.omgservers.schema.entrypoint.player.CreateClientPlayerRequest;
import com.omgservers.schema.entrypoint.player.CreateClientPlayerResponse;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.client.SyncClientRequest;
import com.omgservers.schema.module.client.SyncClientResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.SelectTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
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
import com.omgservers.service.security.ServiceSecurityAttributesEnum;
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

    final RuntimeAssignmentModelFactory runtimeAssignmentModelFactory;
    final ClientModelFactory clientModelFactory;
    final PlayerModelFactory playerModelFactory;

    final SecurityIdentity securityIdentity;

    @Override
    public Uni<CreateClientPlayerResponse> execute(final CreateClientPlayerRequest request) {
        log.debug("Requested, {}, principal={}", request, securityIdentity.getPrincipal().getName());

        final var userId =
                securityIdentity.<Long>getAttribute(ServiceSecurityAttributesEnum.USER_ID.getAttributeName());

        final var tenantId = request.getTenantId();
        final var tenantStageId = request.getStageId();
        final var tenantStageSecret = request.getSecret();

        return validateStageSecret(tenantId, tenantStageId, tenantStageSecret)
                .flatMap(rawToken -> findOrCreatePlayer(userId, tenantId, tenantStageId)
                        .flatMap(player -> {
                            final var playerId = player.getId();
                            return createClient(userId, playerId, tenantId, tenantStageId)
                                    .flatMap(client -> syncClient(client)
                                            .replaceWith(client.getId()));
                        })
                )
                .invoke(clientId -> log.info("The new client \"{}\" was created by the user {}", clientId, userId))
                .map(CreateClientPlayerResponse::new);
    }

    Uni<TenantStageModel> validateStageSecret(final Long tenantId,
                                              final Long tenantStageId,
                                              final String secret) {
        final var request = new GetTenantStageRequest(tenantId, tenantStageId);
        return tenantModule.getService().getTenantStage(request)
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
                                        final Long tennatStageId) {
        return findPlayer(userId, tennatStageId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithUni(t -> createPlayer(userId, tenantId, tennatStageId));
    }

    Uni<PlayerModel> findPlayer(final Long userId, final Long tenantStageId) {
        final var request = new FindPlayerRequest(userId, tenantStageId);
        return userModule.getService().findPlayer(request)
                .map(FindPlayerResponse::getPlayer);
    }

    Uni<PlayerModel> createPlayer(final Long userId,
                                  final Long tenantId,
                                  final Long tenantStageId) {
        final var player = playerModelFactory.create(userId, tenantId, tenantStageId);
        final var syncPlayerRequest = new SyncPlayerRequest(player);
        return userModule.getService().syncPlayer(syncPlayerRequest)
                .replaceWith(player);
    }

    Uni<ClientModel> createClient(final Long userId,
                                  final Long playerId,
                                  final Long tenantId,
                                  final Long tenantStageId) {
        return selectTenantDeployment(tenantId, tenantStageId)
                .map(tenantDeployment -> {
                    final var tenantDeploymentId = tenantDeployment.getId();
                    final var client = clientModelFactory.create(userId,
                            playerId,
                            tenantId,
                            tenantDeploymentId);

                    return client;
                });
    }

    Uni<TenantDeploymentModel> selectTenantDeployment(final Long tenantId, final Long tenantStageId) {
        final var request = new SelectTenantDeploymentRequest(tenantId,
                tenantStageId,
                SelectTenantDeploymentRequest.StrategyEnum.LATEST);
        return tenantModule.getService().selectTenantDeployment(request)
                .map(SelectTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<Boolean> syncClient(ClientModel client) {
        final var request = new SyncClientRequest(client);
        return clientModule.getService().syncClient(request)
                .map(SyncClientResponse::getCreated);
    }
}
