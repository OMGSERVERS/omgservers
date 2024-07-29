package com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.deleteVersionLobbyRef;

import com.omgservers.schema.event.body.module.tenant.VersionLobbyRefDeletedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteVersionLobbyRefOperationImpl implements DeleteVersionLobbyRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteVersionLobbyRef(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final Long tenantId,
                                              final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_tenant_version_lobby_ref
                        set modified = $3, deleted = true
                        where tenant_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        tenantId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new VersionLobbyRefDeletedEventBodyModel(tenantId, id),
                () -> null
        );
    }
}
