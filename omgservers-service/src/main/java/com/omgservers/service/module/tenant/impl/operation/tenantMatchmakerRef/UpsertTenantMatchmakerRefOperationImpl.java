package com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRefCreatedEventBodyModel;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertTenantMatchmakerRefOperationImpl implements UpsertTenantMatchmakerRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantMatchmakerRefModel tenantMatchmakerRef) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_matchmaker_ref(
                            id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantMatchmakerRef.getId(),
                        tenantMatchmakerRef.getIdempotencyKey(),
                        tenantMatchmakerRef.getTenantId(),
                        tenantMatchmakerRef.getDeploymentId(),
                        tenantMatchmakerRef.getCreated().atOffset(ZoneOffset.UTC),
                        tenantMatchmakerRef.getModified().atOffset(ZoneOffset.UTC),
                        tenantMatchmakerRef.getMatchmakerId(),
                        tenantMatchmakerRef.getDeleted()
                ),
                () -> new TenantMatchmakerRefCreatedEventBodyModel(tenantMatchmakerRef.getTenantId(),
                        tenantMatchmakerRef.getId()),
                () -> null
        );
    }
}