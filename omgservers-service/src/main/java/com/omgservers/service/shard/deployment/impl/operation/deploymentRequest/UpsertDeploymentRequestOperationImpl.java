package com.omgservers.service.shard.deployment.impl.operation.deploymentRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
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
class UpsertDeploymentRequestOperationImpl implements UpsertDeploymentRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final DeploymentRequestModel deploymentRequest) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_deployment_request(
                            id, idempotency_key, deployment_id, created, modified, client_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        deploymentRequest.getId(),
                        deploymentRequest.getIdempotencyKey(),
                        deploymentRequest.getDeploymentId(),
                        deploymentRequest.getCreated().atOffset(ZoneOffset.UTC),
                        deploymentRequest.getModified().atOffset(ZoneOffset.UTC),
                        deploymentRequest.getClientId(),
                        deploymentRequest.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
