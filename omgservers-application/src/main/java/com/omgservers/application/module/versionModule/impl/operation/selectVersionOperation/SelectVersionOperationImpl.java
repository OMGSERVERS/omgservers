package com.omgservers.application.module.versionModule.impl.operation.selectVersionOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.model.version.VersionStageConfigModel;
import com.omgservers.model.version.VersionStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionOperationImpl implements SelectVersionOperation {

    static private final String sql = """
            select id, created, modified, tenant_id, stage_id, stage_config, source_code, bytecode, status, errors
            from $schema.tab_version where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionModel> selectVersion(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long id) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            VersionModel version = createVersion(iterator.next());
                            log.info("Version was found, version={}", version);
                            return version;
                        } catch (IOException e) {
                            throw new ServerSideInternalException("version can't be parsed, id=" + id);
                        }
                    } else {
                        throw new ServerSideNotFoundException(String.format("version was not found, id=%s", id));
                    }
                });
    }

    VersionModel createVersion(Row row) throws IOException {
        VersionModel version = new VersionModel();
        version.setCreated(row.getOffsetDateTime("created").toInstant());
        version.setModified(row.getOffsetDateTime("modified").toInstant());
        version.setId(row.getLong("id"));
        version.setTenantId(row.getLong("tenant_id"));
        version.setStageId(row.getLong("stage_id"));
        version.setStageConfig(objectMapper.readValue(row.getString("stage_config"), VersionStageConfigModel.class));
        version.setSourceCode(objectMapper.readValue(row.getString("source_code"), VersionSourceCodeModel.class));
        version.setBytecode(objectMapper.readValue(row.getString("bytecode"), VersionBytecodeModel.class));
        version.setStatus(VersionStatusEnum.valueOf(row.getString("status")));
        version.setErrors(row.getString("errors"));
        return version;
    }
}
