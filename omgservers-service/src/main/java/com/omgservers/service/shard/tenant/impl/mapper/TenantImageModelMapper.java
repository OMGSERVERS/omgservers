package com.omgservers.service.shard.tenant.impl.mapper;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantImageModelMapper {

    public TenantImageModel execute(final Row row) {
        final var tenantImage = new TenantImageModel();
        tenantImage.setId(row.getLong("id"));
        tenantImage.setIdempotencyKey(row.getString("idempotency_key"));
        tenantImage.setTenantId(row.getLong("tenant_id"));
        tenantImage.setVersionId(row.getLong("version_id"));
        tenantImage.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantImage.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantImage.setQualifier(TenantImageQualifierEnum.valueOf(row.getString("qualifier")));
        tenantImage.setImageId(row.getString("image_id"));
        tenantImage.setDeleted(row.getBoolean("deleted"));
        return tenantImage;
    }
}
