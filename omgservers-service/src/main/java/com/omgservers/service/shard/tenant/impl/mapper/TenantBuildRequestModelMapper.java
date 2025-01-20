package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantBuildRequestModelMapper {

    public TenantBuildRequestModel fromRow(final Row row) {
        final var tenantBuildRequest = new TenantBuildRequestModel();
        tenantBuildRequest.setId(row.getLong("id"));
        tenantBuildRequest.setIdempotencyKey(row.getString("idempotency_key"));
        tenantBuildRequest.setTenantId(row.getLong("tenant_id"));
        tenantBuildRequest.setVersionId(row.getLong("version_id"));
        tenantBuildRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantBuildRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantBuildRequest.setQualifier(TenantBuildRequestQualifierEnum.valueOf(row.getString("qualifier")));
        tenantBuildRequest.setBuildNumber(row.getInteger("build_number"));
        tenantBuildRequest.setDeleted(row.getBoolean("deleted"));
        return tenantBuildRequest;
    }
}
