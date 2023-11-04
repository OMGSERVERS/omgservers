package com.omgservers.service.module.tenant.impl.operation.upsertProject;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.project.ProjectModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.time.Duration;

public interface UpsertProjectOperation {
    Uni<Boolean> upsertProject(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               ProjectModel project);

    default Boolean upsertProject(long timeout,
                                  PgPool pgPool,
                                  int shard,
                                  ProjectModel project) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection ->
                            upsertProject(changeContext, sqlConnection, shard, project));
                })
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
