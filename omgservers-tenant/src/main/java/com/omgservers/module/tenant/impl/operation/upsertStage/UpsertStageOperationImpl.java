package com.omgservers.module.tenant.impl.operation.upsertStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.stage.StageModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
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
class UpsertStageOperationImpl implements UpsertStageOperation {

    static private final String sql = """
            insert into $schema.tab_tenant_stage(id, project_id, created, modified, secret, config, matchmaker_id, version_id)
            values($1, $2, $3, $4, $5, $6, $7, $8)
            on conflict (id) do
            nothing
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
                        add(stage.getId());
                        add(stage.getProjectId());
                        add(stage.getCreated().atOffset(ZoneOffset.UTC));
                        add(stage.getModified().atOffset(ZoneOffset.UTC));
                        add(stage.getSecret());
                        add(configString);
                        add(stage.getMatchmakerId());
                        add(stage.getVersionId());
                    }}))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
