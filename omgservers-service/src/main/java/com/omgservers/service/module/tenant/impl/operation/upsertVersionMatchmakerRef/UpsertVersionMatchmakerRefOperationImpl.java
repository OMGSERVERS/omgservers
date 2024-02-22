package com.omgservers.service.module.tenant.impl.operation.upsertVersionMatchmakerRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.VersionMatchmakerRefCreatedEventBodyModel;
import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertVersionMatchmakerRefOperationImpl implements UpsertVersionMatchmakerRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertVersionMatchmakerRef(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final int shard,
                                                   final VersionMatchmakerRefModel versionMatchmakerRef) {
        final var tenantId = versionMatchmakerRef.getTenantId();
        final var id = versionMatchmakerRef.getId();

        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_matchmaker_ref(
                            id, tenant_id, version_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        id,
                        tenantId,
                        versionMatchmakerRef.getVersionId(),
                        versionMatchmakerRef.getCreated().atOffset(ZoneOffset.UTC),
                        versionMatchmakerRef.getModified().atOffset(ZoneOffset.UTC),
                        versionMatchmakerRef.getMatchmakerId(),
                        versionMatchmakerRef.getDeleted()
                ),
                () -> new VersionMatchmakerRefCreatedEventBodyModel(tenantId, id),
                () -> null
        );
    }
}
