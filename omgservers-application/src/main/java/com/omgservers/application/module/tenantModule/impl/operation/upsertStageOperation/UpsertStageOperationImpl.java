package com.omgservers.application.module.tenantModule.impl.operation.upsertStageOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertStageOperationImpl implements UpsertStageOperation {

    static private final String sql = """
            insert into $schema.tab_project_stage(project_uuid, created, modified, uuid, secret, matchmaker, config, version)
            values($1, $2, $3, $4, $5, $6, $7, $8)
            on conflict (uuid) do
            update set modified = $3, secret = $5, matchmaker = $6, config = $7, version = $8
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertStage(final SqlConnection sqlConnection,
                                    final int shard,
                                    final StageModel stage) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (stage == null) {
            throw new ServerSideBadRequestException("stage is null");
        }

        return upsertQuery(sqlConnection, shard, stage)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Stage was inserted, shard={}, stage={}", shard, stage);
                    } else {
                        log.info("Stage was updated, shard={}, stage={}", shard, stage);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, stage=%s", t.getMessage(), stage)));
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, StageModel stage) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(stage.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(stage.getProject());
                        add(stage.getCreated().atOffset(ZoneOffset.UTC));
                        add(stage.getModified().atOffset(ZoneOffset.UTC));
                        add(stage.getUuid());
                        add(stage.getSecret());
                        add(stage.getMatchmaker());
                        add(configString);
                        add(stage.getVersion());
                    }}))
                    .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
