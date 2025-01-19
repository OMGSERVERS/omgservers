package com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest;

import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import com.omgservers.service.event.body.module.tenant.TenantLobbyRequestCreatedEventBodyModel;
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
class UpsertTenantLobbyRequestOperationImpl implements UpsertTenantLobbyRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantLobbyRequestModel tenantLobbyRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_lobby_request(
                            id, idempotency_key, tenant_id, deployment_id, created, modified, lobby_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantLobbyRequest.getId(),
                        tenantLobbyRequest.getIdempotencyKey(),
                        tenantLobbyRequest.getTenantId(),
                        tenantLobbyRequest.getDeploymentId(),
                        tenantLobbyRequest.getCreated().atOffset(ZoneOffset.UTC),
                        tenantLobbyRequest.getModified().atOffset(ZoneOffset.UTC),
                        tenantLobbyRequest.getLobbyId(),
                        tenantLobbyRequest.getDeleted()
                ),
                () -> new TenantLobbyRequestCreatedEventBodyModel(tenantLobbyRequest.getTenantId(),
                        tenantLobbyRequest.getId()),
                () -> null
        );
    }
}
