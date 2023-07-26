package com.omgservers.application.module.tenantModule.impl.operation.selectStageOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.tenantModule.model.stage.StageConfigModel;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectStageOperationImpl implements SelectStageOperation {

    static private final String sql = """
            select id, project_id, created, modified, secret, matchmaker_id, config, version_id
            from $schema.tab_project_stage
            where id = $1
            limit 1
            """;

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
                            throw new ServerSideInternalException("stage config can't be parsed, id=" + id);
                        }
                    } else {
                        throw new ServerSideNotFoundException("stage was not found, id=" + id);
                    }
                });
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
        stage.setVersionId(row.getLong("version_id"));
        return stage;
    }
}
