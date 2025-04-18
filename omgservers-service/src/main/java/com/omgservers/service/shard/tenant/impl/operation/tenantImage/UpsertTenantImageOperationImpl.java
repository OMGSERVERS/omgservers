package com.omgservers.service.shard.tenant.impl.operation.tenantImage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.event.body.module.tenant.TenantImageCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantImageOperationImpl implements UpsertTenantImageOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final TenantImageModel tenantImage) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_tenant_image(
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, config,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantImage.getId(),
                        tenantImage.getIdempotencyKey(),
                        tenantImage.getTenantId(),
                        tenantImage.getVersionId(),
                        tenantImage.getCreated().atOffset(ZoneOffset.UTC),
                        tenantImage.getModified().atOffset(ZoneOffset.UTC),
                        tenantImage.getQualifier(),
                        tenantImage.getImageId(),
                        getConfigString(tenantImage),
                        tenantImage.getDeleted()
                ),
                () -> new TenantImageCreatedEventBodyModel(tenantImage.getTenantId(), tenantImage.getId()),
                () -> null
        );
    }

    String getConfigString(final TenantImageModel tenantImage) {
        try {
            return objectMapper.writeValueAsString(tenantImage.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
