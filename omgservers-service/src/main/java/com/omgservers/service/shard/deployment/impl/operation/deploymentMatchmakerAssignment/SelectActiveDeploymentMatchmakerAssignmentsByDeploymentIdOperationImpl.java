package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.deployment.impl.mapper.DeploymentMatchmakerAssignmentModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperationImpl
        implements SelectActiveDeploymentMatchmakerAssignmentsByDeploymentIdOperation {

    final SelectListOperation selectListOperation;

    final DeploymentMatchmakerAssignmentModelMapper deploymentMatchmakerAssignmentModelMapper;

    @Override
    public Uni<List<DeploymentMatchmakerAssignmentModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long deploymentId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, client_id, matchmaker_id, deleted
                        from $schema.tab_deployment_matchmaker_assignment
                        where deployment_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(deploymentId),
                "Deployment matchmaker assignment",
                deploymentMatchmakerAssignmentModelMapper::execute);
    }
}
