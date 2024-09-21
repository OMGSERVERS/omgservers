package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantJenkinsRequestModelMapper {

    public TenantJenkinsRequestModel fromRow(final Row row) {
        final var versionJenkinsRequest = new TenantJenkinsRequestModel();
        versionJenkinsRequest.setId(row.getLong("id"));
        versionJenkinsRequest.setIdempotencyKey(row.getString("idempotency_key"));
        versionJenkinsRequest.setTenantId(row.getLong("tenant_id"));
        versionJenkinsRequest.setVersionId(row.getLong("version_id"));
        versionJenkinsRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        versionJenkinsRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        versionJenkinsRequest.setQualifier(TenantJenkinsRequestQualifierEnum.valueOf(row.getString("qualifier")));
        versionJenkinsRequest.setBuildNumber(row.getInteger("build_number"));
        versionJenkinsRequest.setDeleted(row.getBoolean("deleted"));
        return versionJenkinsRequest;
    }
}
