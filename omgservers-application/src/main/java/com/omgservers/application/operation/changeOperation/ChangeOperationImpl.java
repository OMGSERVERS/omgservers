package com.omgservers.application.operation.changeOperation;

import com.omgservers.application.Dispatcher;
import com.omgservers.application.InternalRequest;
import com.omgservers.application.ShardModel;
import com.omgservers.application.module.internalModule.model.event.EventBodyModel;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventModelFactory;
import com.omgservers.application.module.internalModule.model.log.LogModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.operation.upsertEventOperation.UpsertEventOperation;
import com.omgservers.application.operation.upsertLogOperation.UpsertLogOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ChangeOperationImpl implements ChangeOperation {

    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final Dispatcher dispatcher;
    final PgPool pgPool;

    @Override
    public Uni<Boolean> change(final InternalRequest request,
                               final BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction) {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        changeFunction.apply(sqlConnection, shardModel)));
    }

    @Override
    public Uni<Boolean> changeWithLog(final InternalRequest request,
                                      final BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction,
                                      final Function<Boolean, LogModel> logFunction) {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        changeFunction.apply(sqlConnection, shardModel)
                                .flatMap(result -> upsertLog(sqlConnection, result, logFunction)
                                        .map(changeLog -> new TransactionResult(result, changeLog, null))))
                )
                .map(transactionResult -> transactionResult.result);
    }

    @Override
    public Uni<Boolean> changeWithEvent(final InternalRequest request,
                                        final BiFunction<SqlConnection, ShardModel, Uni<Boolean>> changeFunction,
                                        final Function<Boolean, LogModel> logFunction,
                                        final Function<Boolean, EventBodyModel> eventBodyFunction) {
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> pgPool.withTransaction(sqlConnection ->
                        changeFunction.apply(sqlConnection, shardModel)
                                .flatMap(result -> upsertLog(sqlConnection, result, logFunction)
                                        .flatMap(changeLog -> upsertEvent(sqlConnection, result, eventBodyFunction)
                                                .map(insertedEvent -> new TransactionResult(result, changeLog, insertedEvent)))))
                )
                .invoke(transactionResult -> {
                    final var event = transactionResult.insertedEvent;
                    if (event != null) {
                        cacheEvent(transactionResult.insertedEvent);
                    }
                })
                .map(transactionResult -> transactionResult.result);
    }

    Uni<LogModel> upsertLog(final SqlConnection sqlConnection,
                            final boolean result,
                            final Function<Boolean, LogModel> logFunction) {
        final var changeLog = logFunction.apply(result);
        if (changeLog != null) {
            return upsertLogOperation.upsertLog(sqlConnection, changeLog)
                    .replaceWith(changeLog);
        } else {
            return Uni.createFrom().nullItem();
        }
    }

    Uni<EventModel> upsertEvent(final SqlConnection sqlConnection,
                                final boolean result,
                                final Function<Boolean, EventBodyModel> eventBodyFunction) {
        final var body = eventBodyFunction.apply(result);
        if (body != null) {
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(sqlConnection, event)
                    .replaceWith(event);
        } else {
            return Uni.createFrom().nullItem();
        }
    }

    void cacheEvent(final EventModel event) {
        final var eventId = event.getId();
        final var groupId = event.getGroupId();
        dispatcher.addEvent(eventId, groupId);
    }

    private record TransactionResult(boolean result,
                                     LogModel changeLog,
                                     EventModel insertedEvent) {
    }
}
