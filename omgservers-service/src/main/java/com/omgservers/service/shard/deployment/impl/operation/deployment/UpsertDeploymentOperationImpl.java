package com.omgservers.service.shard.deployment.impl.operation.deployment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertDeploymentOperationImpl implements UpsertDeploymentOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final DeploymentModel deployment) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_deployment(
                            id, idempotency_key, created, modified, tenant_id, stage_id, version_id, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        deployment.getId(),
                        deployment.getIdempotencyKey(),
                        deployment.getCreated().atOffset(ZoneOffset.UTC),
                        deployment.getModified().atOffset(ZoneOffset.UTC),
                        deployment.getTenantId(),
                        deployment.getStageId(),
                        deployment.getVersionId(),
                        getConfigString(deployment),
                        deployment.getDeleted()
                ),
                () -> new DeploymentCreatedEventBodyModel(deployment.getId()),
                () -> null
        );
    }

    String getConfigString(final DeploymentModel deployment) {
        try {
            return objectMapper.writeValueAsString(deployment.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
