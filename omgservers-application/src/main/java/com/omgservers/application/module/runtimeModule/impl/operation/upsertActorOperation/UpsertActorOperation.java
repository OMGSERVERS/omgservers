package com.omgservers.application.module.runtimeModule.impl.operation.upsertActorOperation;

import com.omgservers.application.module.runtimeModule.model.actor.ActorModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertActorOperation {
    Uni<Boolean> upsertActor(SqlConnection sqlConnection, int shard, ActorModel actor);

    default Boolean upsertActor(long timeout, PgPool pgPool, int shard, ActorModel actor) {
        return pgPool.withTransaction(sqlConnection -> upsertActor(sqlConnection, shard, actor))
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
