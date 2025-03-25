package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.model.poolCommand.body.DeleteContainerPoolCommandBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.module.pool.poolCommand.SyncPoolCommandResponse;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.GetRuntimeResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.service.factory.pool.PoolCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.GetDefaultPoolIdOperation;
import com.omgservers.service.operation.job.FindAndDeleteJobOperation;
import com.omgservers.service.operation.runtime.DeleteRuntimeCommandsOperation;
import com.omgservers.service.operation.runtime.DeleteRuntimeMessagesOperation;
import com.omgservers.service.operation.server.GetServersOperation;
import com.omgservers.service.service.job.JobService;
import com.omgservers.service.shard.lobby.LobbyShard;
import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.pool.PoolShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.tenant.TenantShard;
import com.omgservers.service.shard.user.UserShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerShard matchmakerShard;
    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;
    final LobbyShard lobbyShard;
    final UserShard userShard;
    final PoolShard poolShard;

    final JobService jobService;

    final GetServersOperation getServersOperation;

    final DeleteRuntimeCommandsOperation deleteRuntimeCommandsOperation;
    final DeleteRuntimeMessagesOperation deleteRuntimeMessagesOperation;
    final FindAndDeleteJobOperation findAndDeleteJobOperation;
    final GetDefaultPoolIdOperation getDefaultPoolIdOperation;

    final PoolCommandModelFactory poolCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RuntimeDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    log.debug("Deleted, {}", runtime);

                    // TODO: cleanup container user

                    return deleteRuntimeCommandsOperation.execute(runtimeId)
                            .flatMap(voidItem -> deleteRuntimeMessagesOperation.execute(runtimeId))
                            .flatMap(voidItem -> getDefaultPoolIdOperation.execute().flatMap(defaultPoolId ->
                                    createDeleteContainerPoolCommand(defaultPoolId,
                                            runtimeId,
                                            idempotencyKey)))
                            .flatMap(voidItem -> findAndDeleteJobOperation.execute(runtimeId));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> createDeleteContainerPoolCommand(final Long poolId,
                                                  final Long runtimeId,
                                                  final String idempotencyKey) {
        final var commandBody = new DeleteContainerPoolCommandBodyDto(runtimeId);
        final var poolCommand = poolCommandModelFactory.create(poolId, commandBody, idempotencyKey);

        final var request = new SyncPoolCommandRequest(poolCommand);
        return poolShard.getService().executeWithIdempotency(request)
                .map(SyncPoolCommandResponse::getCreated);
    }
}
