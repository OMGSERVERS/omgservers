package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.poolRequest.PoolRequestConfigDto;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerEnvironment;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerLabel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.alias.GetDefaultPoolIdOperation;
import com.omgservers.service.operation.runtime.SelectTenantImageForRuntimeOperation;
import com.omgservers.service.operation.server.GenerateSecureStringOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
import com.omgservers.service.shard.alias.AliasShard;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.shard.user.UserShard;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CreatePoolRequestOperationImpl implements CreatePoolRequestOperation {

    final DeploymentShard deploymentShard;
    final AliasShard aliasShard;
    final UserShard userShard;
    final PoolShard poolShard;

    final CreatePoolContainerEnvironmentOperation createPoolContainerEnvironmentOperation;
    final SelectTenantImageForRuntimeOperation selectTenantImageForRuntimeOperation;
    final CreatePoolContainerLabelsOperation createPoolContainerLabelsOperation;
    final GenerateSecureStringOperation generateSecureStringOperation;
    final GetServiceConfigOperation getServiceConfigOperation;
    final GetDefaultPoolIdOperation getDefaultPoolIdOperation;

    final PoolRequestModelFactory poolRequestModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public Uni<Boolean> execute(final RuntimeModel runtime,
                                final DeploymentModel deployment,
                                final String idempotencyKey) {

        final var runtimeQualifier = runtime.getQualifier();
        final var tenantId = deployment.getTenantId();
        final var tenantVersionId = deployment.getVersionId();

        return selectTenantImageForRuntimeOperation.execute(runtimeQualifier, tenantId, tenantVersionId)
                .flatMap(tenantImage -> createPoolContainerLabelsOperation.execute(runtime, deployment)
                        .flatMap(labels -> {
                            final var userId = runtime.getUserId();
                            final var password = generateSecureStringOperation.generateSecureString();
                            final var image = tenantImage.getImageId();
                            return createUser(userId, password, idempotencyKey)
                                    .flatMap(created -> createPoolContainerEnvironmentOperation.execute(
                                            runtime, password))
                                    .flatMap(environment -> createPoolRequest(runtime,
                                            image,
                                            environment,
                                            labels,
                                            idempotencyKey));
                        }));
    }

    Uni<Boolean> createUser(final Long id,
                            final String password,
                            final String idempotencyKey) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(id, UserRoleEnum.RUNTIME, passwordHash, idempotencyKey);
        final var request = new SyncUserRequest(user);
        return userShard.getService().syncUserWithIdempotency(request)
                .map(SyncUserResponse::getCreated);
    }

    public Uni<Boolean> createPoolRequest(final RuntimeModel runtime,
                                          final String image,
                                          final HashMap<PoolContainerEnvironment, String> environment,
                                          final Map<PoolContainerLabel, String> labels,
                                          final String idempotencyKey) {
        return getDefaultPoolIdOperation.execute()
                .flatMap(defaultPoolId -> {
                    final var containerConfig = new PoolRequestConfigDto.ContainerConfig();
                    containerConfig.setImage(image);
                    containerConfig.setLabels(labels);

                    // TODO: get limits from version config
                    final var defaultCpuLimit = getServiceConfigOperation.getServiceConfig()
                            .runtimes().defaultCpuLimit();
                    containerConfig.setCpuLimitInMilliseconds(defaultCpuLimit);
                    final var defaultMemoryLimit = getServiceConfigOperation.getServiceConfig()
                            .runtimes().defaultMemoryLimit();
                    containerConfig.setMemoryLimitInMegabytes(defaultMemoryLimit);

                    containerConfig.setEnvironment(environment);

                    final var poolRequestConfig = new PoolRequestConfigDto();
                    poolRequestConfig.setContainerConfig(containerConfig);

                    final var runtimeId = runtime.getId();
                    final var runtimeQualifier = runtime.getQualifier();
                    final var poolRequest = poolRequestModelFactory.create(defaultPoolId,
                            runtimeId,
                            runtimeQualifier,
                            poolRequestConfig,
                            idempotencyKey);

                    return syncPoolRequest(poolRequest);
                });
    }

    Uni<Boolean> syncPoolRequest(final PoolRequestModel poolRequest) {
        final var request = new SyncPoolRequestRequest(poolRequest);
        return poolShard.getService().executeWithIdempotency(request)
                .map(SyncPoolRequestResponse::getCreated);
    }
}
