package com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.deployment.impl.mapper.DeploymentCommandModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveDeploymentCommandsByDeploymentIdOperationImpl
        implements SelectActiveDeploymentCommandsByDeploymentIdOperation {

    final SelectListOperation selectListOperation;

    final DeploymentCommandModelMapper deploymentCommandModelMapper;

    @Override
    public Uni<List<DeploymentCommandModel>> execute(final SqlConnection sqlConnection,
                                                     final int slot,
                                                     final Long deploymentId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select 
                            id, idempotency_key, deployment_id, created, modified, qualifier, body, deleted
                        from $slot.tab_deployment_command
                        where deployment_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(deploymentId),
                "Deployment command",
                deploymentCommandModelMapper::execute);
    }
}
