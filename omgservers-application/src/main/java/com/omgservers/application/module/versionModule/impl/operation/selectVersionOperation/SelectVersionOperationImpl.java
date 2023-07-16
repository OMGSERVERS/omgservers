package com.omgservers.application.module.versionModule.impl.operation.selectVersionOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.versionModule.model.VersionBytecodeModel;
import com.omgservers.application.module.versionModule.model.VersionSourceCodeModel;
import com.omgservers.application.module.versionModule.model.VersionModel;
import com.omgservers.application.module.versionModule.model.VersionStatusEnum;
import com.omgservers.application.module.versionModule.model.VersionStageConfigModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideInternalException;
import com.omgservers.application.exception.ServerSideNotFoundException;
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
class SelectVersionOperationImpl implements SelectVersionOperation {

    static private final String sql = """
            select created, modified, uuid, tenant, stage, stage_config, source_code, bytecode, status, errors
            from $schema.tab_version where uuid = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionModel> selectVersion(final SqlConnection sqlConnection,
                                           final int shard,
                                           final UUID uuid) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(uuid))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            VersionModel version = createVersion(iterator.next());
                            log.info("Version was found, version={}", version);
                            return version;
                        } catch (IOException e) {
                            throw new ServerSideInternalException("version can't be parsed, uuid=" + uuid);
                        }
                    } else {
                        throw new ServerSideNotFoundException(String.format("version was not found, uuid=%s", uuid));
                    }
                });
    }

    VersionModel createVersion(Row row) throws IOException {
        VersionModel version = new VersionModel();
        version.setCreated(row.getOffsetDateTime("created").toInstant());
        version.setModified(row.getOffsetDateTime("modified").toInstant());
        version.setUuid(row.getUUID("uuid"));
        version.setTenant(row.getUUID("tenant"));
        version.setStage(row.getUUID("stage"));
        version.setStageConfig(objectMapper.readValue(row.getString("stage_config"), VersionStageConfigModel.class));
        version.setSourceCode(objectMapper.readValue(row.getString("source_code"), VersionSourceCodeModel.class));
        version.setBytecode(objectMapper.readValue(row.getString("bytecode"), VersionBytecodeModel.class));
        version.setStatus(VersionStatusEnum.valueOf(row.getString("status")));
        version.setErrors(row.getString("errors"));
        return version;
    }
}
