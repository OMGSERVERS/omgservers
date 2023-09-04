package com.omgservers.module.tenant.impl.operation.selectStage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectStageOperationImpl implements SelectStageOperation {

    static private final String sql = """
            select id, project_id, created, modified, secret, matchmaker_id, config
            from $schema.tab_tenant_stage
            where id = $1
            limit 1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<StageModel> selectStage(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            log.info("Stage was found, id={}", id);
                            return createStage(iterator.next());
                        } catch (IOException e) {
                            throw new ServerSideConflictException("stage config can't be parsed, id=" + id);
                        }
                    } else {
                        throw new ServerSideNotFoundException("stage was not found, id=" + id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    StageModel createStage(Row row) throws IOException {
        StageModel stage = new StageModel();
        stage.setId(row.getLong("id"));
        stage.setProjectId(row.getLong("project_id"));
        stage.setCreated(row.getOffsetDateTime("created").toInstant());
        stage.setModified(row.getOffsetDateTime("modified").toInstant());
        stage.setSecret(row.getString("secret"));
        stage.setMatchmakerId(row.getLong("matchmaker_id"));
        stage.setConfig(objectMapper.readValue(row.getString("config"), StageConfigModel.class));
        return stage;
    }
}
