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
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectStageOperationImpl implements SelectStageOperation {

    static private final String sql = """
            select project_uuid, created, modified, uuid, secret, matchmaker, config, version
            from $schema.tab_project_stage
            where uuid = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<StageModel> selectStage(final SqlConnection sqlConnection,
                                       final int shard,
                                       final UUID uuid) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(uuid))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            log.info("Stage was found, uuid={}", uuid);
                            return createStage(iterator.next());
                        } catch (IOException e) {
                            throw new ServerSideInternalException("stage config can't be parsed, uuid=" + uuid);
                        }
                    } else {
                        throw new ServerSideNotFoundException("stage was not found, uuid=" + uuid);
                    }
                });
    }

    StageModel createStage(Row row) throws IOException {
        StageModel stage = new StageModel();
        stage.setProject(row.getUUID("project_uuid"));
        stage.setCreated(row.getOffsetDateTime("created").toInstant());
        stage.setModified(row.getOffsetDateTime("modified").toInstant());
        stage.setUuid(row.getUUID("uuid"));
        stage.setSecret(row.getString("secret"));
        stage.setMatchmaker(row.getUUID("matchmaker"));
        stage.setConfig(objectMapper.readValue(row.getString("config"), StageConfigModel.class));
        stage.setVersion(row.getUUID("version"));
        return stage;
    }
}
