package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantImage.TenantImageConfigDto;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantImageModelMapper {

    final ObjectMapper objectMapper;

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
        try {
            tenantImage.setConfig(objectMapper.readValue(row.getString("config"),
                    TenantImageConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "tenant image config can't be parsed, tenantImage=" + tenantImage, e);
        }
        return tenantImage;
    }
}
