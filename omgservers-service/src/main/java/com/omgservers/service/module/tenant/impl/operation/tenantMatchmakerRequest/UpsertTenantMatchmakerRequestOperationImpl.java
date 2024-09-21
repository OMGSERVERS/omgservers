package com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.event.body.module.tenant.TenantMatchmakerRequestCreatedEventBodyModel;
import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
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
class UpsertTenantMatchmakerRequestOperationImpl implements UpsertTenantMatchmakerRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final TenantMatchmakerRequestModel tenantMatchmakerRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_tenant_matchmaker_request(
                            id, idempotency_key, tenant_id, deployment_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        tenantMatchmakerRequest.getId(),
                        tenantMatchmakerRequest.getIdempotencyKey(),
                        tenantMatchmakerRequest.getTenantId(),
                        tenantMatchmakerRequest.getDeploymentId(),
                        tenantMatchmakerRequest.getCreated().atOffset(ZoneOffset.UTC),
                        tenantMatchmakerRequest.getModified().atOffset(ZoneOffset.UTC),
                        tenantMatchmakerRequest.getMatchmakerId(),
                        tenantMatchmakerRequest.getDeleted()
                ),
                () -> new TenantMatchmakerRequestCreatedEventBodyModel(tenantMatchmakerRequest.getTenantId(),
                        tenantMatchmakerRequest.getId()),
                () -> null
        );
    }
}
