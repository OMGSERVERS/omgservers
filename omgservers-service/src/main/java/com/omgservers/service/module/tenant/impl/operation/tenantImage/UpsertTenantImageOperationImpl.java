package com.omgservers.service.module.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.event.body.module.tenant.TenantImageCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantImageOperationImpl implements UpsertTenantImageOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantImageModel tenantImage) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_image(
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
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
                        tenantImage.getDeleted()
                ),
                () -> new TenantImageCreatedEventBodyModel(tenantImage.getTenantId(), tenantImage.getId()),
                () -> null
        );
    }
}
