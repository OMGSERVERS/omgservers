package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.service.event.body.module.tenant.TenantLobbyResourceCreatedEventBodyModel;
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
class UpsertTenantLobbyResourceOperationImpl implements UpsertTenantLobbyResourceOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantLobbyResourceModel tenantLobbyResource) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_lobby_resource(
                            id, idempotency_key, tenant_id, deployment_id, created, modified, lobby_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantLobbyResource.getId(),
                        tenantLobbyResource.getIdempotencyKey(),
                        tenantLobbyResource.getTenantId(),
                        tenantLobbyResource.getDeploymentId(),
                        tenantLobbyResource.getCreated().atOffset(ZoneOffset.UTC),
                        tenantLobbyResource.getModified().atOffset(ZoneOffset.UTC),
                        tenantLobbyResource.getLobbyId(),
                        tenantLobbyResource.getDeleted()
                ),
                () -> new TenantLobbyResourceCreatedEventBodyModel(tenantLobbyResource.getTenantId(),
                        tenantLobbyResource.getId()),
                () -> null
        );
    }
}
