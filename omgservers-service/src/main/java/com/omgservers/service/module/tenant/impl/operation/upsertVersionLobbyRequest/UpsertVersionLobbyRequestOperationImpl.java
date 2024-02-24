package com.omgservers.service.module.tenant.impl.operation.upsertVersionLobbyRequest;

import com.omgservers.model.event.body.VersionLobbyRequestCreatedEventBodyModel;
import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
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
class UpsertVersionLobbyRequestOperationImpl implements UpsertVersionLobbyRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertVersionLobbyRequest(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final VersionLobbyRequestModel versionLobbyRequest) {
        final var tenantId = versionLobbyRequest.getTenantId();
        final var id = versionLobbyRequest.getId();

        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_version_lobby_request(
                            id, tenant_id, version_id, created, modified, lobby_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        id,
                        tenantId,
                        versionLobbyRequest.getVersionId(),
                        versionLobbyRequest.getCreated().atOffset(ZoneOffset.UTC),
                        versionLobbyRequest.getModified().atOffset(ZoneOffset.UTC),
                        versionLobbyRequest.getLobbyId(),
                        versionLobbyRequest.getDeleted()
                ),
                () -> new VersionLobbyRequestCreatedEventBodyModel(tenantId, id),
                () -> null
        );
    }
}