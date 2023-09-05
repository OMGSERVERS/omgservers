package com.omgservers.operation.changeWithContext;

import com.omgservers.ChangeContext;
import com.omgservers.Dispatcher;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.log.LogModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ChangeWithContextOperationImpl implements ChangeWithContextOperation {

    final Dispatcher dispatcher;
    final PgPool pgPool;

    @Override
    public <T> Uni<ChangeContext<T>> changeWithContext(BiFunction<ChangeContext<T>, SqlConnection, Uni<T>> changeFunction) {
        return Uni.createFrom().context(context -> {
            final var changeContext = new ChangeContext<T>(context);
            return pgPool.withTransaction(sqlConnection -> changeFunction.apply(changeContext, sqlConnection))
                    .invoke(result -> {
                        final var changeEvents = changeContext.getChangeEvents();
                        final var logEvents = changeContext.getChangeLogs();
                        log.info("Changed with context, events={}, result={}, logs={}",
                                changeEvents.stream().map(EventModel::getQualifier).toList(),
                                result, logEvents.stream().map(LogModel::getMessage).toList());
                        changeContext.setResult(result);
                        // cache events
                        changeEvents.forEach(event ->
                                dispatcher.addEvent(event.getId(), event.getGroupId()));
                    })
                    .replaceWith(changeContext);
        });
    }
}
