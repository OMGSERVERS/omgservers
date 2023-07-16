package com.omgservers.application.module.tenantModule.impl.operation.insertStageOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.exception.ServerSideNotFoundException;
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
class InsertStageOperationImpl implements InsertStageOperation {

    static private final String sql = """
            insert into $schema.tab_project_stage(project_uuid, created, modified, uuid, secret, matchmaker, config, version)
            values($1, $2, $3, $4, $5, $6, $7, $8)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertStage(final SqlConnection sqlConnection,
                                 final int shard,
                                 final StageModel stage) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (stage == null) {
            throw new ServerSideBadRequestException("stage is null");
        }

        return insertQuery(sqlConnection, shard, stage)
                .invoke(inserted -> log.info("Stage was inserted, shard={}, stage={}", shard, stage))
                .onFailure(PgException.class)
                .transform(t -> {
                    final var pgException = (PgException) t;
                    final var code = pgException.getSqlState();
                    final var column = pgException.getColumn();
                    if (code.equals("23503") ) {
                        // foreign_key_violation
                        return new ServerSideNotFoundException("project was not found, uuid=" + stage.getProject());
                    } else {
                        return new ServerSideConflictException("unhandled PgException, " + t.getMessage());
                    }
                });
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, StageModel stage) {
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
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
