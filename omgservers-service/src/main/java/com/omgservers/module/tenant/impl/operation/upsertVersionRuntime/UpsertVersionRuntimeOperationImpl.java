package com.omgservers.module.tenant.impl.operation.upsertVersionRuntime;

import com.omgservers.model.event.body.VersionRuntimeCreatedEventBodyModel;
import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertVersionRuntimeOperationImpl implements UpsertVersionRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertVersionRuntime(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final VersionRuntimeModel stageRuntime) {
        final var tenantId = stageRuntime.getTenantId();
        final var id = stageRuntime.getId();

        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_runtime(
                            id, tenant_id, version_id, created, modified, runtime_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        id,
                        tenantId,
                        stageRuntime.getVersionId(),
                        stageRuntime.getCreated().atOffset(ZoneOffset.UTC),
                        stageRuntime.getModified().atOffset(ZoneOffset.UTC),
                        stageRuntime.getRuntimeId(),
                        stageRuntime.getDeleted()
                ),
                () -> new VersionRuntimeCreatedEventBodyModel(tenantId, id),
                () -> logModelFactory.create(String.format("Version runtime was created, " +
                        "tenantId=%d, id=%d", tenantId, id))
        );
    }
}
