package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.message.MessageModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.GetRuntimeResponse;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.shard.runtime.runtimeAssignment.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.InterchangeMessagesResponse;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.server.cache.CacheService;
import com.omgservers.service.server.cache.dto.CacheRuntimeLastActivityRequest;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.runtime.impl.operation.handleOutgoingMessages.HandleOutgoingMessageOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeMessage.DeleteRuntimeMessagesByIdsOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeMessage.SelectActiveRuntimeMessagesByRuntimeIdOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InterchangeMessagesMethodImpl implements InterchangeMessagesMethod {

    final RuntimeShard runtimeShard;
    final CacheService cacheService;

    final SelectActiveRuntimeMessagesByRuntimeIdOperation selectActiveRuntimeMessagesByRuntimeIdOperation;
    final DeleteRuntimeMessagesByIdsOperation deleteRuntimeMessagesByIdsOperation;
    final HandleOutgoingMessageOperation handleOutgoingMessageOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final PgPool pgPool;

    @Override
    public Uni<InterchangeMessagesResponse> execute(final ShardModel shardModel,
                                                    final InterchangeMessagesRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    if (runtime.getDeleted()) {
                        throw new ServerSideNotFoundException(ExceptionQualifierEnum.RUNTIME_NOT_FOUND,
                                "runtime already deleted, runtimeId=" + runtimeId);
                    }

                    return cacheRuntimeLastActivity(runtimeId)
                            .flatMap(voidItem -> viewRuntimeAssignments(runtimeId))
                            .flatMap(runtimeAssignments -> {
                                final int shard = shardModel.shard();
                                final var consumedMessages = request.getConsumedMessages();
                                return handleOutgoingMessages(runtime,
                                        runtimeAssignments,
                                        request.getOutgoingMessages())
                                        .flatMap(voidItem -> receiveMessages(shard,
                                                runtimeId,
                                                consumedMessages));
                            });
                })
                .map(InterchangeMessagesResponse::new);
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeRequest(id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Void> cacheRuntimeLastActivity(final Long runtimeId) {
        final var lastActivity = Instant.now();
        final var request = new CacheRuntimeLastActivityRequest(runtimeId, lastActivity);
        return cacheService.execute(request)
                .replaceWithVoid();
    }

    Uni<List<RuntimeAssignmentModel>> viewRuntimeAssignments(final Long runtimeId) {
        final var request = new ViewRuntimeAssignmentsRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(ViewRuntimeAssignmentsResponse::getRuntimeAssignments);
    }

    Uni<Void> handleOutgoingMessages(final RuntimeModel runtime,
                                     final List<RuntimeAssignmentModel> runtimeAssignments,
                                     final List<MessageModel> outgoingMessages) {
        return Multi.createFrom().iterable(outgoingMessages)
                .onItem().transformToUniAndConcatenate(outgoingMessage -> handleOutgoingMessageOperation
                        .execute(runtime, runtimeAssignments, outgoingMessage)
                        .onFailure()
                        .recoverWithUni(t -> {
                            log.warn("Failed, runtimeId={}, qualifier={}, {}:{}",
                                    runtime.getId(),
                                    outgoingMessage.getQualifier(),
                                    t.getClass().getSimpleName(),
                                    t.getMessage());
                            return Uni.createFrom().voidItem();
                        }))
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<List<RuntimeMessageModel>> receiveMessages(final int shard,
                                                   final Long runtimeId,
                                                   final List<Long> consumedCommands) {
        return changeWithContextOperation.<List<RuntimeMessageModel>>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeMessagesByIdsOperation.execute(changeContext,
                                        sqlConnection,
                                        shard,
                                        runtimeId,
                                        consumedCommands)
                                .flatMap(deleted -> selectActiveRuntimeMessagesByRuntimeIdOperation
                                        .execute(sqlConnection,
                                                shard,
                                                runtimeId))
                )
                .map(ChangeContext::getResult);
    }
}
