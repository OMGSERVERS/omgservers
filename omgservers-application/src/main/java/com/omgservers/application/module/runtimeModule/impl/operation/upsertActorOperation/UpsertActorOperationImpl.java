package com.omgservers.application.module.runtimeModule.impl.operation.upsertActorOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.runtimeModule.model.actor.ActorModel;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertActorOperationImpl implements UpsertActorOperation {

    static private final String sql = """
            insert into $schema.tab_runtime_actor(id, runtime_id, created, modified, user_id, client_id, config, status)
            values($1, $2, $3, $4, $5, $6, $7, $8)
            on conflict (id) do
            update set modified = $4, user_id = $5, client_id = $6, config = $7, status = $8
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertActor(final SqlConnection sqlConnection,
                                    final int shard,
                                    final ActorModel actor) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (actor == null) {
            throw new ServerSideBadRequestException("actor is null");
        }

        return upsertQuery(sqlConnection, shard, actor)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Actor was inserted, shard={}, actor={}", shard, actor);
                    } else {
                        log.info("Actor was updated, shard={}, actor={}", shard, actor);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> {
                    final var pgException = (PgException) t;
                    final var code = pgException.getSqlState();
                    if (code.equals("23503")) {
                        // foreign_key_violation
                        return new ServerSideNotFoundException("runtime was not found, actor=" + actor);
                    } else {
                        return new ServerSideInternalException(String.format("unhandled PgException, " +
                                "%s, actor=%s", t.getMessage(), actor));
                    }
                });
    }

    Uni<Boolean> upsertQuery(final SqlConnection sqlConnection,
                             final int shard,
                             final ActorModel actor) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(actor.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(actor.getId());
                        add(actor.getRuntimeId());
                        add(actor.getCreated().atOffset(ZoneOffset.UTC));
                        add(actor.getModified().atOffset(ZoneOffset.UTC));
                        add(actor.getUserId());
                        add(actor.getClientId());
                        add(configString);
                        add(actor.getStatus());
                    }}))
                    .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
