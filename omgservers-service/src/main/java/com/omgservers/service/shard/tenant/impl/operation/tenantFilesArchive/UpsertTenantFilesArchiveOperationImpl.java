package com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.schema.model.tenantFilesArchive.TenantFilesArchiveModel;
import com.omgservers.service.event.body.module.tenant.TenantFilesArchiveCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Base64;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTenantFilesArchiveOperationImpl implements UpsertTenantFilesArchiveOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantFilesArchiveModel tenantFilesArchive) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_files_archive(
                            id, idempotency_key, tenant_id, version_id, created, modified, archive, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantFilesArchive.getId(),
                        tenantFilesArchive.getIdempotencyKey(),
                        tenantFilesArchive.getTenantId(),
                        tenantFilesArchive.getVersionId(),
                        tenantFilesArchive.getCreated().atOffset(ZoneOffset.UTC),
                        tenantFilesArchive.getModified().atOffset(ZoneOffset.UTC),
                        getArchiveBytes(tenantFilesArchive),
                        tenantFilesArchive.getDeleted()
                ),
                () -> new TenantFilesArchiveCreatedEventBodyModel(tenantFilesArchive.getTenantId(),
                        tenantFilesArchive.getId()),
                () -> null
        );
    }

    byte[] getArchiveBytes(final TenantFilesArchiveModel tenantFilesArchive) {
        return Base64.getDecoder().decode(tenantFilesArchive.getBase64Archive());
    }
}
