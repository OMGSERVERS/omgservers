package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerResourceCreatedEventBodyModel;
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
class UpsertTenantMatchmakerResourceOperationImpl implements UpsertTenantMatchmakerResourceOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantMatchmakerResourceModel tenantMatchmakerResource) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_matchmaker_resource(
                            id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantMatchmakerResource.getId(),
                        tenantMatchmakerResource.getIdempotencyKey(),
                        tenantMatchmakerResource.getTenantId(),
                        tenantMatchmakerResource.getDeploymentId(),
                        tenantMatchmakerResource.getCreated().atOffset(ZoneOffset.UTC),
                        tenantMatchmakerResource.getModified().atOffset(ZoneOffset.UTC),
                        tenantMatchmakerResource.getMatchmakerId(),
                        tenantMatchmakerResource.getDeleted()
                ),
                () -> new TenantMatchmakerResourceCreatedEventBodyModel(tenantMatchmakerResource.getTenantId(),
                        tenantMatchmakerResource.getId()),
                () -> null
        );
    }
}
