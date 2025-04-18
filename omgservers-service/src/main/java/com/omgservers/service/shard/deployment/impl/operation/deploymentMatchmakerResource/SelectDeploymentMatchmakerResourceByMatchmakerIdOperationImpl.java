package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectDeploymentMatchmakerResourceByMatchmakerIdOperationImpl
        implements SelectDeploymentMatchmakerResourceByMatchmakerIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final DeploymentMatchmakerResourceModelMapper deploymentMatchmakerResourceModelMapper;

    @Override
    public Uni<DeploymentMatchmakerResourceModel> execute(final SqlConnection sqlConnection,
                                                          final int slot,
                                                          final Long deploymentId,
                                                          final Long matchmakerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, matchmaker_id, status, deleted
                        from $slot.tab_deployment_matchmaker_resource
                        where deployment_id = $1 and matchmaker_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(deploymentId, matchmakerId
                ),
                "Deployment matchmaker version",
                deploymentMatchmakerResourceModelMapper::execute);
    }
}
