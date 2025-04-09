package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectTenantVersionConfigOperationImpl implements SelectTenantVersionConfigOperation {

    final SelectObjectOperation selectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<TenantVersionConfigDto> execute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long tenantId,
                                               final Long tenantVersionId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select config
                        from $shard.tab_tenant_version
                        where tenant_id = $1 and id = $2
                        limit 1
                        """,
                List.of(tenantId, tenantVersionId),
                "Tenant version config",
                row -> {
                    try {
                        return objectMapper.readValue(row.getString("config"), TenantVersionConfigDto.class);
                    } catch (IOException e) {
                        throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                                String.format("config can't be parsed, tenantId=%d, versionId=%d",
                                        tenantId, tenantVersionId), e);
                    }
                });
    }
}
