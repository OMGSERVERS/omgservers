package com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertDeploymentCommandOperationImpl implements UpsertDeploymentCommandOperation {

    final ChangeObjectOperation changeObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final DeploymentCommandModel deploymentCommand) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_deployment_command(
                            id, idempotency_key, deployment_id, created, modified, qualifier, body, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        deploymentCommand.getId(),
                        deploymentCommand.getIdempotencyKey(),
                        deploymentCommand.getDeploymentId(),
                        deploymentCommand.getCreated().atOffset(ZoneOffset.UTC),
                        deploymentCommand.getModified().atOffset(ZoneOffset.UTC),
                        deploymentCommand.getQualifier(),
                        getBodyString(deploymentCommand),
                        deploymentCommand.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getBodyString(final DeploymentCommandModel deploymentCommand) {
        try {
            return objectMapper.writeValueAsString(deploymentCommand.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
