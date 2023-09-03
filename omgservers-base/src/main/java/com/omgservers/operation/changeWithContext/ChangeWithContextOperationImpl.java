package com.omgservers.operation.changeWithContext;

import com.omgservers.ChangeContext;
import com.omgservers.Dispatcher;
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
    public <T> Uni<T> changeWithContext(BiFunction<ChangeContext, SqlConnection, Uni<T>> changeFunction) {
        return Uni.createFrom().context(context -> {
            final var changeContext = new ChangeContext(context);
            return pgPool.withTransaction(sqlConnection -> changeFunction.apply(changeContext, sqlConnection))
                    .invoke(result -> {
                        // cache events
                        changeContext.getChangeEvents().forEach(event ->
                                dispatcher.addEvent(event.getId(), event.getGroupId()));
                    });
        });
    }
}
