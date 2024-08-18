package com.omgservers.service.handler.internal;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolRequest.PoolRequestConfigDto;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.user.UserModel;
import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import com.omgservers.schema.model.versionImageRef.VersionImageRefQualifierEnum;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestRequest;
import com.omgservers.schema.module.pool.poolRequest.SyncPoolRequestResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.tenant.GetVersionRequest;
import com.omgservers.schema.module.tenant.GetVersionResponse;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsRequest;
import com.omgservers.schema.module.tenant.versionImageRef.ViewVersionImageRefsResponse;
import com.omgservers.schema.module.user.GetUserRequest;
import com.omgservers.schema.module.user.GetUserResponse;
import com.omgservers.schema.module.user.SyncUserRequest;
import com.omgservers.schema.module.user.SyncUserResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.internal.RuntimeDeploymentRequestedEventBodyModel;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.pool.PoolRequestModelFactory;
import com.omgservers.service.factory.user.UserModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.pool.PoolModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.generateSecureString.GenerateSecureStringOperation;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeDeploymentRequestedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final PoolModule poolModule;
    final UserModule userModule;

    final GenerateSecureStringOperation generateSecureStringOperation;
    final GetConfigOperation getConfigOperation;

    final PoolRequestModelFactory poolRequestModelFactory;
    final UserModelFactory userModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DEPLOYMENT_REQUESTED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeDeploymentRequestedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.info("Runtime deployment was requested, runtimeId={}, qualifier={}",
                            runtimeId, runtime.getQualifier());

                    final var tenantId = runtime.getTenantId();
                    final var versionId = runtime.getVersionId();
                    return getVersion(tenantId, versionId)
                            .flatMap(version -> viewVersionImageRef(tenantId, versionId)
                                    .map(imageRefs -> selectImageRef(runtime, imageRefs))
                                    .flatMap(imageRef -> {
                                        final var userId = runtime.getUserId();
                                        final var password = generateSecureStringOperation.generateSecureString();
                                        final var idempotencyKey = event.getId().toString();
                                        final var imageId = imageRef.getImageId();
                                        return createUser(userId, password, idempotencyKey)
                                                .flatMap(user -> syncPoolRequest(runtime, user, password, imageId));
                                    })
                            );
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<VersionModel> getVersion(final Long tenantId, final Long id) {
        final var request = new GetVersionRequest(tenantId, id);
        return tenantModule.getVersionService().getVersion(request)
                .map(GetVersionResponse::getVersion);
    }

    Uni<List<VersionImageRefModel>> viewVersionImageRef(final Long tenantId, final Long versionId) {
        final var request = new ViewVersionImageRefsRequest(tenantId, versionId);
        return tenantModule.getVersionService().viewVersionImageRefs(request)
                .map(ViewVersionImageRefsResponse::getVersionImageRefs);
    }

    VersionImageRefModel selectImageRef(final RuntimeModel runtime,
                                        final List<VersionImageRefModel> imageRefs) {
        final var universalImageRefOptional = getImageByQualifier(imageRefs,
                VersionImageRefQualifierEnum.UNIVERSAL);
        return universalImageRefOptional
                .orElseGet(() -> switch (runtime.getQualifier()) {
                    case LOBBY -> {
                        final var lobbyImageRefOptional = getImageByQualifier(imageRefs,
                                VersionImageRefQualifierEnum.LOBBY);
                        if (lobbyImageRefOptional.isPresent()) {
                            yield lobbyImageRefOptional.get();
                        } else {
                            throw new ServerSideConflictException(
                                    ExceptionQualifierEnum.IMAGE_NOT_FOUND,
                                    "lobby image was not found");
                        }
                    }
                    case MATCH -> {
                        final var matchImageRefOptional = getImageByQualifier(imageRefs,
                                VersionImageRefQualifierEnum.MATCH);
                        if (matchImageRefOptional.isPresent()) {
                            yield matchImageRefOptional.get();
                        } else {
                            throw new ServerSideConflictException(
                                    ExceptionQualifierEnum.IMAGE_NOT_FOUND,
                                    "match image was not found");
                        }
                    }
                });
    }

    Optional<VersionImageRefModel> getImageByQualifier(final List<VersionImageRefModel> imageRefs,
                                                       final VersionImageRefQualifierEnum qualifier) {
        return imageRefs.stream()
                .filter(imageRef -> imageRef.getQualifier().equals(qualifier))
                .findFirst();
    }

    Uni<UserModel> createUser(final Long id,
                              final String password,
                              final String idempotencyKey) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(id, UserRoleEnum.RUNTIME, passwordHash, idempotencyKey);
        final var request = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(request)
                .map(SyncUserResponse::getCreated)
                .replaceWith(user)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", user, t.getMessage());

                            // User was already created
                            final var getUserRequest = new GetUserRequest(id);
                            return userModule.getUserService().getUser(getUserRequest)
                                    .map(GetUserResponse::getUser);

                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    Uni<Boolean> syncPoolRequest(final RuntimeModel runtime,
                                 final UserModel user,
                                 final String password,
                                 final String imageId) {
        final var defaultPoolId = getConfigOperation.getServiceConfig().defaults().poolId();
        final var poolRequestConfig = new PoolRequestConfigDto();
        poolRequestConfig.setServerContainerConfig(new PoolRequestConfigDto.ServerContainerConfig());
        poolRequestConfig.getServerContainerConfig().setImageId(imageId);
        // TODO: get limits from version config
        final var defaultCpuLimit = getConfigOperation.getServiceConfig().runtimes().defaultCpuLimit();
        poolRequestConfig.getServerContainerConfig().setCpuLimitInMilliseconds(defaultCpuLimit);
        final var defaultMemoryLimit = getConfigOperation.getServiceConfig().runtimes().defaultMemoryLimit();
        poolRequestConfig.getServerContainerConfig().setMemoryLimitInMegabytes(defaultMemoryLimit);
        final var serviceUri = getConfigOperation.getServiceConfig().runtimes().serviceUri();
        final var environment = new HashMap<String, String>();
        environment.put("OMGSERVERS_URL", serviceUri.toString());
        environment.put("OMGSERVERS_USER_ID", user.getId().toString());
        environment.put("OMGSERVERS_PASSWORD", password);
        environment.put("OMGSERVERS_RUNTIME_ID", runtime.getId().toString());
        environment.put("OMGSERVERS_RUNTIME_QUALIFIER", runtime.getQualifier().toString());
        poolRequestConfig.getServerContainerConfig().setEnvironment(environment);

        final var poolRequest = poolRequestModelFactory.create(defaultPoolId,
                runtime.getId(),
                runtime.getQualifier(),
                poolRequestConfig);

        return syncPoolRequest(poolRequest);
    }

    Uni<Boolean> syncPoolRequest(final PoolRequestModel poolRequest) {
        final var request = new SyncPoolRequestRequest(poolRequest);
        return poolModule.getPoolService().syncPoolRequestWithIdempotency(request)
                .map(SyncPoolRequestResponse::getCreated);
    }
}
