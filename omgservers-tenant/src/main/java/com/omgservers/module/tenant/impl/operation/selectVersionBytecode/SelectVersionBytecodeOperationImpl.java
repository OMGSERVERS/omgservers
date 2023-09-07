package com.omgservers.module.tenant.impl.operation.selectVersionBytecode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionBytecodeOperationImpl implements SelectVersionBytecodeOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionBytecodeModel> selectVersionBytecode(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long versionId) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select bytecode
                        from $schema.tab_tenant_version
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(versionId),
                "Version",
                row -> {
                    try {
                        return objectMapper.readValue(row.getString("bytecode"), VersionBytecodeModel.class);
                    } catch (IOException e) {
                        throw new ServerSideConflictException("bytecode can't be parsed, versionId=" + versionId, e);
                    }
                });
    }
}
