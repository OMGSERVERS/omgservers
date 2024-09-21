package com.omgservers.service.module.tenant.impl.operation.tenantImageRef;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.service.event.body.module.tenant.TenantImageRefCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertTenantImageRefOperationImpl implements UpsertTenantImageRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantImageRefModel tenantImageRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_image_ref(
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantImageRef.getId(),
                        tenantImageRef.getIdempotencyKey(),
                        tenantImageRef.getTenantId(),
                        tenantImageRef.getVersionId(),
                        tenantImageRef.getCreated().atOffset(ZoneOffset.UTC),
                        tenantImageRef.getModified().atOffset(ZoneOffset.UTC),
                        tenantImageRef.getQualifier(),
                        tenantImageRef.getImageId(),
                        tenantImageRef.getDeleted()
                ),
                () -> new TenantImageRefCreatedEventBodyModel(tenantImageRef.getTenantId(), tenantImageRef.getId()),
                () -> null
        );
    }
}
