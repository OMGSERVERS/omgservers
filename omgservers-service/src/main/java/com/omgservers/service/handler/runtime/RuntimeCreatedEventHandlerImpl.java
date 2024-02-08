package com.omgservers.service.handler.runtime;

import com.omgservers.model.container.ContainerConfigModel;
import com.omgservers.model.container.ContainerQualifierEnum;
import com.omgservers.model.dto.user.SyncUserRequest;
import com.omgservers.model.dto.user.SyncUserResponse;
import com.omgservers.model.entitiy.EntityQualifierEnum;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.job.JobQualifierEnum;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.service.factory.ContainerModelFactory;
import com.omgservers.service.factory.EntityModelFactory;
import com.omgservers.service.factory.JobModelFactory;
import com.omgservers.service.factory.RuntimePermissionModelFactory;
import com.omgservers.service.factory.UserModelFactory;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.operation.getConfig.GetConfigOperation;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.HashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final SystemModule systemModule;
    final UserModule userModule;

    final GetConfigOperation getConfigOperation;

    final RuntimePermissionModelFactory runtimePermissionModelFactory;
    final ContainerModelFactory containerModelFactory;
    final EntityModelFactory entityModelFactory;
    final UserModelFactory userModelFactory;
    final JobModelFactory jobModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        return runtimeModule.getShortcutService().getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.info("Runtime was created, id={}, type={}",
                            runtime.getId(),
                            runtime.getQualifier());

                    return syncRuntimeJob(runtimeId)
                            .flatMap(wasJobCreated -> createContainer(runtime))
                            .flatMap(voidItem -> {
                                final var entity = entityModelFactory
                                        .create(runtimeId, EntityQualifierEnum.RUNTIME);
                                return systemModule.getShortcutService().syncEntity(entity);
                            });
                })
                .replaceWith(true);
    }

    Uni<Boolean> syncRuntimeJob(final Long runtimeId) {
        final var job = jobModelFactory.create(runtimeId, runtimeId, JobQualifierEnum.RUNTIME);
        return systemModule.getShortcutService().syncJob(job);
    }

    Uni<Void> createContainer(final RuntimeModel runtime) {
        // TODO: improve it
        final var password = String.valueOf(new SecureRandom().nextLong());
        return createUser(password)
                .flatMap(user -> {
                    final var runtimeId = runtime.getId();
                    final var userId = user.getId();
                    return createRuntimePermission(runtimeId, userId)
                            .flatMap(wasRuntimePermissionCreated -> syncContainer(userId, password, runtime)
                                    .replaceWithVoid());
                });
    }

    Uni<UserModel> createUser(final String password) {
        final var passwordHash = BcryptUtil.bcryptHash(password);
        final var user = userModelFactory.create(UserRoleEnum.WORKER, passwordHash);
        final var request = new SyncUserRequest(user);
        return userModule.getUserService().syncUser(request)
                .map(SyncUserResponse::getCreated)
                .replaceWith(user);
    }

    Uni<Boolean> createRuntimePermission(final Long runtimeId, final Long userId) {
        final var runtimePermission = runtimePermissionModelFactory.create(runtimeId,
                userId,
                RuntimePermissionEnum.HANDLE_RUNTIME);
        return runtimeModule.getShortcutService().syncRuntimePermission(runtimePermission);
    }

    Uni<Boolean> syncContainer(final Long userId,
                               final String password,
                               final RuntimeModel runtime) {
        final var runtimeId = runtime.getId();
        final var workerImage = getConfigOperation.getConfig().workersImage();
        final var internalUri = getConfigOperation.getConfig().internalUri();
        final var environment = new HashMap<String, String>();
        environment.put("OMGSERVERS_URL", internalUri.toString());
        environment.put("OMGSERVERS_USER_ID", userId.toString());
        environment.put("OMGSERVERS_PASSWORD", password);
        environment.put("OMGSERVERS_RUNTIME_ID", runtimeId.toString());
        environment.put("OMGSERVERS_RUNTIME_QUALIFIER", runtime.getQualifier().toString());
        final var config = new ContainerConfigModel(environment);
        final var container = containerModelFactory.create(runtimeId,
                ContainerQualifierEnum.RUNTIME,
                workerImage,
                config);
        return systemModule.getShortcutService().syncContainer(container);
    }
}
