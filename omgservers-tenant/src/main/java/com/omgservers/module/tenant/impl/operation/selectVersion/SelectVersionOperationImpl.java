package com.omgservers.module.tenant.impl.operation.selectVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionOperationImpl implements SelectVersionOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionModel> selectVersion(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long id) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, stage_id, created, modified, config, source_code, bytecode, errors
                        from $schema.tab_tenant_version where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Version",
                this::createVersion);
    }

    VersionModel createVersion(Row row) {
        VersionModel version = new VersionModel();
        version.setId(row.getLong("id"));
        version.setStageId(row.getLong("stage_id"));
        version.setCreated(row.getOffsetDateTime("created").toInstant());
        version.setModified(row.getOffsetDateTime("modified").toInstant());

        try {
            version.setConfig(objectMapper.readValue(row.getString("config"), VersionConfigModel.class));
            version.setSourceCode(objectMapper.readValue(row.getString("source_code"), VersionSourceCodeModel.class));
            version.setBytecode(objectMapper.readValue(row.getString("bytecode"), VersionBytecodeModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("version can't be parsed, version=" + version, e);
        }

        version.setErrors(row.getString("errors"));
        return version;
    }
}
