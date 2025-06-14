package com.omgservers.service.operation.pool;

import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.poolContainer.PoolContainerEnvironmentEnum;
import com.omgservers.schema.model.poolContainer.PoolContainerLabel;
import com.omgservers.schema.model.poolRequest.PoolRequestConfigDto;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.user.UserConfigDto;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.shard.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.shard.user.SyncUserRequest;
import com.omgservers.schema.shard.user.SyncUserResponse;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.operation.alias.GetDefaultPoolIdOperation;
import com.omgservers.service.operation.runtime.SelectTenantImageForRuntimeOperation;
import com.omgservers.service.operation.server.GenerateSecureStringOperation;
import com.omgservers.service.operation.server.GetServiceConfigOperation;
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

    final UserShard userShard;
    final PoolShard poolShard;

    final CreatePoolContainerEnvironmentOperation createPoolContainerEnvironmentOperation;
    final GetPoolContainerMemoryLimitOperation getPoolContainerMemoryLimitOperation;
    final SelectTenantImageForRuntimeOperation selectTenantImageForRuntimeOperation;
    final CreatePoolContainerLabelsOperation createPoolContainerLabelsOperation;
    final GetPoolContainerCpuLimitOperation getPoolContainerCpuLimitOperation;
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
                                            deployment.getConfig(),
                                            idempotencyKey));
                        }));
    }

    Uni<Boolean> createUser(final Long id,
                            final String password,
                            final String idempotencyKey) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(id,
                UserRoleEnum.RUNTIME,
                passwordHash,
                UserConfigDto.create(),
                idempotencyKey);
        final var request = new SyncUserRequest(user);
        return userShard.getService().executeWithIdempotency(request)
                .map(SyncUserResponse::getCreated);
    }

    public Uni<Boolean> createPoolRequest(final RuntimeModel runtime,
                                          final String image,
                                          final HashMap<PoolContainerEnvironmentEnum, String> environment,
                                          final Map<PoolContainerLabel, String> labels,
                                          final DeploymentConfigDto deploymentConfig,
                                          final String idempotencyKey) {
        return getDefaultPoolIdOperation.execute()
                .flatMap(defaultPoolId -> {
                    final var containerConfig = new PoolRequestConfigDto.ContainerConfig();
                    containerConfig.setImage(image);
                    containerConfig.setLabels(labels);

                    // TODO: get limits from version config

                    final var cpuLimit = getPoolContainerCpuLimitOperation
                            .execute(runtime.getQualifier(), deploymentConfig);
                    containerConfig.setCpuLimitInMilliseconds(cpuLimit);

                    final var memoryLimit = getPoolContainerMemoryLimitOperation
                            .execute(runtime.getQualifier(), deploymentConfig);
                    containerConfig.setMemoryLimitInMegabytes(memoryLimit);

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
