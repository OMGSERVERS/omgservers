package com.omgservers.module.system.impl.operation.deleteServiceAccount;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface DeleteServiceAccountOperation {
    Uni<Boolean> deleteServiceAccount(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      Long id);

    default Boolean deleteServiceAccount(long timeout, PgPool pgPool, Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            deleteServiceAccount(changeContext, sqlConnection, id));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
