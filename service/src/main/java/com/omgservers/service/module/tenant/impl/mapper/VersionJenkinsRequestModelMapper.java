package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestModel;
import com.omgservers.model.versionJenkinsRequest.VersionJenkinsRequestQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionJenkinsRequestModelMapper {

    public VersionJenkinsRequestModel fromRow(final Row row) {
        final var versionJenkinsRequest = new VersionJenkinsRequestModel();
        versionJenkinsRequest.setId(row.getLong("id"));
        versionJenkinsRequest.setIdempotencyKey(row.getString("idempotency_key"));
        versionJenkinsRequest.setTenantId(row.getLong("tenant_id"));
        versionJenkinsRequest.setVersionId(row.getLong("version_id"));
        versionJenkinsRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        versionJenkinsRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        versionJenkinsRequest.setQualifier(VersionJenkinsRequestQualifierEnum.valueOf(row.getString("qualifier")));
        versionJenkinsRequest.setBuildNumber(row.getInteger("build_number"));
        versionJenkinsRequest.setDeleted(row.getBoolean("deleted"));
        return versionJenkinsRequest;
    }
}
