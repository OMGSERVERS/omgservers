package com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.upsertVersionLobbyRequest;

import com.omgservers.schema.event.body.module.tenant.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
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
class UpsertVersionLobbyRequestOperationImpl implements UpsertVersionLobbyRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertVersionLobbyRequest(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final VersionLobbyRequestModel versionLobbyRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_lobby_request(
                            id, idempotency_key, tenant_id, version_id, created, modified, lobby_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        versionLobbyRequest.getId(),
                        versionLobbyRequest.getIdempotencyKey(),
                        versionLobbyRequest.getTenantId(),
                        versionLobbyRequest.getVersionId(),
                        versionLobbyRequest.getCreated().atOffset(ZoneOffset.UTC),
                        versionLobbyRequest.getModified().atOffset(ZoneOffset.UTC),
                        versionLobbyRequest.getLobbyId(),
                        versionLobbyRequest.getDeleted()
                ),
                () -> new VersionLobbyRequestCreatedEventBodyModel(versionLobbyRequest.getTenantId(),
                        versionLobbyRequest.getId()),
                () -> null
        );
    }
}
