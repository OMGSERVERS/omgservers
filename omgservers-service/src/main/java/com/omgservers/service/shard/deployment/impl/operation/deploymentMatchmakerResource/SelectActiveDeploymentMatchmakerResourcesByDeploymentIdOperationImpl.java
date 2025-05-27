package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.deployment.impl.mapper.DeploymentMatchmakerResourceModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperationImpl
        implements SelectActiveDeploymentMatchmakerResourcesByDeploymentIdOperation {

    final SelectListOperation selectListOperation;

    final DeploymentMatchmakerResourceModelMapper deploymentMatchmakerResourceModelMapper;

    @Override
    public Uni<List<DeploymentMatchmakerResourceModel>> execute(final SqlConnection sqlConnection,
                                                                final int slot,
                                                                final Long deploymentId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, matchmaker_id, status, config, 
                            deleted
                        from $slot.tab_deployment_matchmaker_resource
                        where deployment_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(deploymentId),
                "Deployment matchmaker resource",
                deploymentMatchmakerResourceModelMapper::execute);
    }
}
