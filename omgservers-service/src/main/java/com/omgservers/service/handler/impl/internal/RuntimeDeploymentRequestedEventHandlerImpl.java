package com.omgservers.service.handler.impl.internal;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolRequest.PoolRequestConfigDto;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.alias.FindAliasRequest;
import com.omgservers.schema.module.alias.FindAliasResponse;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.GetTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesRequest;
import com.omgservers.schema.module.tenant.tenantImage.ViewTenantImagesResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.service.configuration.DefaultAliasConfiguration;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.RuntimeDeploymentRequestedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.alias.AliasModule;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeDeploymentRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final AliasModule aliasModule;
    final PoolModule poolModule;
    final UserModule userModule;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final CalculateShardOperation calculateShardOperation;
    final GetConfigOperation getConfigOperation;

    final PoolRequestModelFactory poolRequestModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DEPLOYMENT_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RuntimeDeploymentRequestedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.debug("Deployment of runtime \"{}\" was requested", runtimeId);

                    final var tenantId = runtime.getTenantId();
                    final var deploymentId = runtime.getDeploymentId();
                    return getTenantDeployment(tenantId, deploymentId)
                            .flatMap(tenantDeployment -> {
                                final var deploymentVersionId = tenantDeployment.getVersionId();
                                return viewTenantImage(tenantId, deploymentVersionId)
                                        .map(tenantImages -> selectTenantImage(runtime, tenantImages))
                                        .flatMap(tenantImage -> {
                                            final var userId = runtime.getUserId();
                                            final var password = generateSecureStringOperation.generateSecureString();
                                            final var idempotencyKey = event.getId().toString();
                                            final var imageId = tenantImage.getImageId();
                                            return createUser(userId, password, idempotencyKey)
                                                    .flatMap(user -> calculateShardOperation
                                                            .calculateShard(runtimeId.toString())
                                                            .flatMap(shard -> syncPoolRequest(runtime,
                                                                    password,
                                                                    imageId,
                                                                    shard.serverUri())));
                                        });
                            });
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<TenantDeploymentModel> getTenantDeployment(final Long tenantId, final Long id) {
        final var request = new GetTenantDeploymentRequest(tenantId, id);
        return tenantModule.getService().getTenantDeployment(request)
                .map(GetTenantDeploymentResponse::getTenantDeployment);
    }

    Uni<List<TenantImageModel>> viewTenantImage(final Long tenantId, final Long tenantVersionId) {
        final var request = new ViewTenantImagesRequest(tenantId, tenantVersionId);
        return tenantModule.getService().viewTenantImages(request)
                .map(ViewTenantImagesResponse::getTenantImages);
    }

    TenantImageModel selectTenantImage(final RuntimeModel runtime,
                                       final List<TenantImageModel> tenantImages) {
        final var universalImageOptional = getImageByQualifier(tenantImages,
                TenantImageQualifierEnum.UNIVERSAL);
        return universalImageOptional
                .orElseGet(() -> switch (runtime.getQualifier()) {
                    case LOBBY -> {
                        final var lobbyImageOptional = getImageByQualifier(tenantImages,
                                TenantImageQualifierEnum.LOBBY);
                        if (lobbyImageOptional.isPresent()) {
                            yield lobbyImageOptional.get();
                        } else {
                            throw new ServerSideConflictException(
                                    ExceptionQualifierEnum.IMAGE_NOT_FOUND,
                                    "lobby image was not found");
                        }
                    }
                    case MATCH -> {
                        final var matchImageOptional = getImageByQualifier(tenantImages,
                                TenantImageQualifierEnum.MATCH);
                        if (matchImageOptional.isPresent()) {
                            yield matchImageOptional.get();
                        } else {
                            throw new ServerSideConflictException(
                                    ExceptionQualifierEnum.IMAGE_NOT_FOUND,
                                    "match image was not found");
                        }
                    }
                });
    }

    Optional<TenantImageModel> getImageByQualifier(final List<TenantImageModel> tenantImages,
                                                   final TenantImageQualifierEnum qualifier) {
        return tenantImages.stream()
                .filter(tenantImage -> tenantImage.getQualifier().equals(qualifier))
                .findFirst();
    }

    Uni<UserModel> createUser(final Long id,
                              final String password,
                              final String idempotencyKey) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(id, UserRoleEnum.RUNTIME, passwordHash, idempotencyKey);
        final var request = new SyncUserRequest(user);
        return userModule.getService().syncUser(request)
                .map(SyncUserResponse::getCreated)
                .replaceWith(user)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}", user, t.getMessage());

                            // User was already created
                            final var getUserRequest = new GetUserRequest(id);
                            return userModule.getService().getUser(getUserRequest)
                                    .map(GetUserResponse::getUser);

                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncPoolRequest(final RuntimeModel runtime,
                                 final String password,
                                 final String imageId,
                                 final URI serverUri) {
        final var poolRequestConfig = new PoolRequestConfigDto();
        poolRequestConfig.setContainerConfig(new PoolRequestConfigDto.ContainerConfig());
        poolRequestConfig.getContainerConfig().setImageId(imageId);
        // TODO: get limits from version config
        final var defaultCpuLimit = getConfigOperation.getServiceConfig().runtimes().defaultCpuLimit();
        poolRequestConfig.getContainerConfig().setCpuLimitInMilliseconds(defaultCpuLimit);
        final var defaultMemoryLimit = getConfigOperation.getServiceConfig().runtimes().defaultMemoryLimit();
        poolRequestConfig.getContainerConfig().setMemoryLimitInMegabytes(defaultMemoryLimit);
        final var environment = new HashMap<String, String>();
        environment.put("OMGSERVERS_RUNTIME_ID", runtime.getId().toString());
        environment.put("OMGSERVERS_PASSWORD", password);
        environment.put("OMGSERVERS_RUNTIME_QUALIFIER", runtime.getQualifier().toString());
        environment.put("OMGSERVERS_SERVICE_URL", serverUri.toString());
        poolRequestConfig.getContainerConfig().setEnvironment(environment);

        return findDefaultPoolAlias()
                .flatMap(alias -> {
                    final var defaultPoolId = alias.getEntityId();

                    final var poolRequest = poolRequestModelFactory.create(defaultPoolId,
                            runtime.getId(),
                            runtime.getQualifier(),
                            poolRequestConfig);

                    return syncPoolRequest(poolRequest);
                });
    }

    Uni<AliasModel> findDefaultPoolAlias() {
        final var request = new FindAliasRequest(DefaultAliasConfiguration.GLOBAL_SHARD_KEY,
                DefaultAliasConfiguration.GLOBAL_ENTITIES_GROUP,
                DefaultAliasConfiguration.DEFAULT_POOL_ALIAS);
        return aliasModule.getService().execute(request)
                .map(FindAliasResponse::getAlias);
    }

    Uni<Boolean> syncPoolRequest(final PoolRequestModel poolRequest) {
        final var request = new SyncPoolRequestRequest(poolRequest);
        return poolModule.getPoolService().executeWithIdempotency(request)
                .map(SyncPoolRequestResponse::getCreated);
    }
}
