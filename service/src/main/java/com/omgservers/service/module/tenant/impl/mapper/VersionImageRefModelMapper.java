package com.omgservers.service.module.tenant.impl.mapper;

import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import com.omgservers.schema.model.versionImageRef.VersionImageRefQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionImageRefModelMapper {

    public VersionImageRefModel fromRow(final Row row) {
        final var versionImageRef = new VersionImageRefModel();
        versionImageRef.setId(row.getLong("id"));
        versionImageRef.setIdempotencyKey(row.getString("idempotency_key"));
        versionImageRef.setTenantId(row.getLong("tenant_id"));
        versionImageRef.setVersionId(row.getLong("version_id"));
        versionImageRef.setCreated(row.getOffsetDateTime("created").toInstant());
        versionImageRef.setModified(row.getOffsetDateTime("modified").toInstant());
        versionImageRef.setQualifier(VersionImageRefQualifierEnum.valueOf(row.getString("qualifier")));
        versionImageRef.setImageId(row.getString("image_id"));
        versionImageRef.setDeleted(row.getBoolean("deleted"));
        return versionImageRef;
    }
}
