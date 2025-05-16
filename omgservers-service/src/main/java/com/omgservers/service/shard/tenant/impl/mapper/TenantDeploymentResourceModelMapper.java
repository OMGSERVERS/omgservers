package com.omgservers.service.shard.tenant.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceConfigDto;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantDeploymentResourceModelMapper {

    final ObjectMapper objectMapper;

    public TenantDeploymentResourceModel execute(final Row row) {
        final var tenantDeploymentResource = new TenantDeploymentResourceModel();
        tenantDeploymentResource.setId(row.getLong("id"));
        tenantDeploymentResource.setIdempotencyKey(row.getString("idempotency_key"));
        tenantDeploymentResource.setTenantId(row.getLong("tenant_id"));
        tenantDeploymentResource.setStageId(row.getLong("stage_id"));
        tenantDeploymentResource.setVersionId(row.getLong("version_id"));
        tenantDeploymentResource.setCreated(row.getOffsetDateTime("created").toInstant());
        tenantDeploymentResource.setModified(row.getOffsetDateTime("modified").toInstant());
        tenantDeploymentResource.setDeploymentId(row.getLong("deployment_id"));
        tenantDeploymentResource.setStatus(TenantDeploymentResourceStatusEnum.valueOf(row.getString("status")));
        tenantDeploymentResource.setDeleted(row.getBoolean("deleted"));
        try {
            tenantDeploymentResource.setConfig(objectMapper.readValue(row.getString("config"),
                    TenantDeploymentResourceConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "tenant deployment resource config can't be parsed, tenantDeploymentResource=" +
                            tenantDeploymentResource, e);
        }
        return tenantDeploymentResource;
    }
}
