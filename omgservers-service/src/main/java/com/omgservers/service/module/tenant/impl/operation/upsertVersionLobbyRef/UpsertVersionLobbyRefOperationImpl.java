package com.omgservers.service.module.tenant.impl.operation.upsertVersionLobbyRef;

import com.omgservers.model.event.body.VersionLobbyRefCreatedEventBodyModel;
import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
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
class UpsertVersionLobbyRefOperationImpl implements UpsertVersionLobbyRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertVersionLobbyRef(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final VersionLobbyRefModel versionLobbyRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_lobby_ref(
                            id, idempotency_key, tenant_id, version_id, created, modified, lobby_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        versionLobbyRef.getId(),
                        versionLobbyRef.getIdempotencyKey(),
                        versionLobbyRef.getTenantId(),
                        versionLobbyRef.getVersionId(),
                        versionLobbyRef.getCreated().atOffset(ZoneOffset.UTC),
                        versionLobbyRef.getModified().atOffset(ZoneOffset.UTC),
                        versionLobbyRef.getLobbyId(),
                        versionLobbyRef.getDeleted()
                ),
                () -> new VersionLobbyRefCreatedEventBodyModel(versionLobbyRef.getTenantId(), versionLobbyRef.getId()),
                () -> null
        );
    }
}
