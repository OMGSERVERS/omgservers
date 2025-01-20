package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyRef;

import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRefCreatedEventBodyModel;
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
class UpsertTenantLobbyRefOperationImpl implements UpsertTenantLobbyRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantLobbyRefModel tenantLobbyRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_lobby_ref(
                            id, idempotency_key, tenant_id, deployment_id, created, modified, lobby_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantLobbyRef.getId(),
                        tenantLobbyRef.getIdempotencyKey(),
                        tenantLobbyRef.getTenantId(),
                        tenantLobbyRef.getDeploymentId(),
                        tenantLobbyRef.getCreated().atOffset(ZoneOffset.UTC),
                        tenantLobbyRef.getModified().atOffset(ZoneOffset.UTC),
                        tenantLobbyRef.getLobbyId(),
                        tenantLobbyRef.getDeleted()
                ),
                () -> new TenantLobbyRefCreatedEventBodyModel(tenantLobbyRef.getTenantId(), tenantLobbyRef.getId()),
                () -> null
        );
    }
}
