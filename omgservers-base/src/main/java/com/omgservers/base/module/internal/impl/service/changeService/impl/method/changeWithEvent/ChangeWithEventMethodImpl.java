package com.omgservers.base.module.internal.impl.service.changeService.impl.method.changeWithEvent;

import com.omgservers.base.factory.EventModelFactory;
import com.omgservers.base.module.internal.impl.Dispatcher;
import com.omgservers.base.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.base.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.dto.internalModule.ChangeWithEventRequest;
import com.omgservers.dto.internalModule.ChangeWithEventResponse;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ChangeWithEventMethodImpl implements ChangeWithEventMethod {

    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final Dispatcher dispatcher;
    final PgPool pgPool;

    @Override
    public Uni<ChangeWithEventResponse> changeWithEvent(final ChangeWithEventRequest request) {
        ChangeWithEventRequest.validate(request);

        final var internalRequest = request.getRequest();
        final var changeFunction = request.getChangeFunction();
        final var logFunction = request.getLogFunction();
        final var eventBodyFunction = request.getEventBodyFunction();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(internalRequest.getRequestShardKey()))
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
                .map(transactionResult -> transactionResult.result)
                .map(ChangeWithEventResponse::new);
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
