package com.omgservers.service.module.tenant.impl.operation.versionImageRef.upsertVersionImageRef;

import com.omgservers.model.event.body.module.tenant.VersionImageRefCreatedEventBodyModel;
import com.omgservers.model.versionImageRef.VersionImageRefModel;
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
class UpsertVersionImageRefOperationImpl implements UpsertVersionImageRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertVersionImageRef(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final VersionImageRefModel versionImageRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_image_ref(
                            id, idempotency_key, tenant_id, version_id, created, modified, qualifier, image_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        versionImageRef.getId(),
                        versionImageRef.getIdempotencyKey(),
                        versionImageRef.getTenantId(),
                        versionImageRef.getVersionId(),
                        versionImageRef.getCreated().atOffset(ZoneOffset.UTC),
                        versionImageRef.getModified().atOffset(ZoneOffset.UTC),
                        versionImageRef.getQualifier(),
                        versionImageRef.getImageId(),
                        versionImageRef.getDeleted()
                ),
                () -> new VersionImageRefCreatedEventBodyModel(versionImageRef.getTenantId(), versionImageRef.getId()),
                () -> null
        );
    }
}
