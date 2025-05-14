package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.model.poolCommand.body.DeleteContainerPoolCommandBodyDto;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeState.RuntimeStateDto;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandRequest;
import com.omgservers.schema.shard.pool.poolCommand.SyncPoolCommandResponse;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateRequest;
import com.omgservers.schema.shard.runtime.runtimeState.GetRuntimeStateResponse;
import com.omgservers.schema.shard.user.DeleteUserRequest;
import com.omgservers.schema.shard.user.DeleteUserResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeDeletedEventBodyModel;
import com.omgservers.service.factory.pool.PoolCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.alias.GetDefaultPoolIdOperation;
import com.omgservers.service.operation.task.DeleteTaskOperation;
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

    final DeleteTaskOperation deleteTaskOperation;
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

        return getRuntimeState(runtimeId)
                .flatMap(runtimeState -> {
                    final var runtime = runtimeState.getRuntime();
                    log.debug("Deleted, {}", runtime);

                    final var runtimeCommands = runtimeState.getRuntimeCommands();
                    final var runtimeAssignments = runtimeState.getRuntimeAssignments();
                    if (!runtimeCommands.isEmpty() || !runtimeAssignments.isEmpty()) {
                        log.error("Runtime \"{}\" deleted, but some data remains, commands={}, assignments={}",
                                runtimeId,
                                runtimeCommands.size(),
                                runtimeAssignments.size());
                    }

                    return deleteRuntimeUser(runtime)
                            .flatMap(voidItem -> getDefaultPoolIdOperation.execute().flatMap(defaultPoolId ->
                                    createDeleteContainerPoolCommand(defaultPoolId,
                                            runtimeId,
                                            idempotencyKey)))
                            .flatMap(voidItem -> deleteTaskOperation.execute(runtimeId));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeStateDto> getRuntimeState(final Long runtimeId) {
        final var request = new GetRuntimeStateRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeStateResponse::getRuntimeState);
    }

    Uni<Boolean> deleteRuntimeUser(final RuntimeModel runtime) {
        final var userId = runtime.getUserId();
        final var request = new DeleteUserRequest(userId);
        return userShard.getService().execute(request)
                .map(DeleteUserResponse::getDeleted);
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
