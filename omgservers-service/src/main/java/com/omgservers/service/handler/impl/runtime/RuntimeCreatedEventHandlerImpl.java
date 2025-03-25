package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.job.JobQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentRequest;
import com.omgservers.schema.module.deployment.deployment.GetDeploymentResponse;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.job.CreateJobOperation;
import com.omgservers.service.operation.pool.CreatePoolRequestOperation;
import com.omgservers.service.operation.runtime.CreateOpenRuntimeCommandOperation;
import com.omgservers.service.operation.runtime.CreateRuntimeCreatedRuntimeMessageOperation;
import com.omgservers.service.service.cache.CacheService;
import com.omgservers.service.shard.deployment.DeploymentShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeCreatedEventHandlerImpl implements EventHandler {

    final DeploymentShard deploymentShard;
    final RuntimeShard runtimeShard;

    final CacheService cacheService;

    final CreateRuntimeCreatedRuntimeMessageOperation createRuntimeCreatedRuntimeMessageOperation;
    final CreateOpenRuntimeCommandOperation createOpenRuntimeCommandOperation;
    final CreatePoolRequestOperation createPoolRequestOperation;
    final CreateJobOperation createJobOperation;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RuntimeCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.debug("Created, {}", runtime);

                    final var deploymentId = runtime.getDeploymentId();
                    return getDeployment(deploymentId)
                            .flatMap(deployment -> createRuntimeCreatedRuntimeMessageOperation
                                    .execute(runtime, idempotencyKey)
                                    .flatMap(created -> createOpenRuntimeCommandOperation
                                            .execute(runtime, idempotencyKey))
                                    .flatMap(created -> createPoolRequestOperation
                                            .execute(runtime, deployment, idempotencyKey))
                                    .flatMap(created -> createJobOperation
                                            .execute(JobQualifierEnum.RUNTIME, runtimeId, idempotencyKey))
                            );
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long runtimeId) {
        final var request = new GetRuntimeRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<DeploymentModel> getDeployment(final Long deploymentId) {
        final var request = new GetDeploymentRequest(deploymentId);
        return deploymentShard.getService().execute(request)
                .map(GetDeploymentResponse::getDeployment);
    }
}
