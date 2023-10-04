package com.omgservers.module.tenant.impl.operation.selectVersionBytecode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionBytecodeOperationImpl implements SelectVersionBytecodeOperation {

    final SelectObjectOperation selectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionBytecodeModel> selectVersionBytecode(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long tenantId,
                                                           final Long versionId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select bytecode
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(tenantId, versionId),
                "Version",
                row -> {
                    try {
                        return objectMapper.readValue(row.getString("bytecode"), VersionBytecodeModel.class);
                    } catch (IOException e) {
                        throw new ServerSideConflictException(String.format("bytecode can't be parsed, " +
                                "tenantId=%d, versionId=%d", tenantId, versionId), e);
                    }
                });
    }
}
