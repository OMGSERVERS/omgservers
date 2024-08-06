package com.omgservers.service.module.tenant.impl.operation.version.selectVersionConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.version.VersionConfigDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.server.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectVersionConfigOperationImpl implements SelectVersionConfigOperation {

    final SelectObjectOperation selectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<VersionConfigDto> selectVersionConfig(final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final Long tenantId,
                                                     final Long versionId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select config
                        from $schema.tab_tenant_version
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, versionId),
                "Version",
                row -> {
                    try {
                        return objectMapper.readValue(row.getString("config"), VersionConfigDto.class);
                    } catch (IOException e) {
                        throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                                String.format("config can't be parsed, tenantId=%d, versionId=%d",
                                        tenantId, versionId), e);
                    }
                });
    }
}
