package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerAssignmentCreatedEventBodyModel;
import com.omgservers.service.factory.system.LogModelFactory;
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
class UpsertDeploymentMatchmakerAssignmentOperationImpl implements UpsertDeploymentMatchmakerAssignmentOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final DeploymentMatchmakerAssignmentModel deploymentMatchmakerAssignmentModel) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_deployment_matchmaker_assignment(
                            id, idempotency_key, deployment_id, created, modified, client_id, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        deploymentMatchmakerAssignmentModel.getId(),
                        deploymentMatchmakerAssignmentModel.getIdempotencyKey(),
                        deploymentMatchmakerAssignmentModel.getDeploymentId(),
                        deploymentMatchmakerAssignmentModel.getCreated().atOffset(ZoneOffset.UTC),
                        deploymentMatchmakerAssignmentModel.getModified().atOffset(ZoneOffset.UTC),
                        deploymentMatchmakerAssignmentModel.getClientId(),
                        deploymentMatchmakerAssignmentModel.getMatchmakerId(),
                        deploymentMatchmakerAssignmentModel.getDeleted()
                ),
                () -> new DeploymentMatchmakerAssignmentCreatedEventBodyModel(deploymentMatchmakerAssignmentModel.getDeploymentId(),
                        deploymentMatchmakerAssignmentModel.getId()),
                () -> null
        );
    }
}
