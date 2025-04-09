package com.omgservers.service.shard.deployment.impl.operation.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.deployment.impl.mapper.DeploymentRequestModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectDeploymentRequestOperationImpl implements SelectDeploymentRequestOperation {

    final SelectObjectOperation selectObjectOperation;

    final DeploymentRequestModelMapper deploymentRequestModelMapper;

    @Override
    public Uni<DeploymentRequestModel> execute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long deploymentId,
                                               final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, client_id, deleted
                        from $shard.tab_deployment_request
                        where deployment_id = $1 and id = $2
                        limit 1
                        """,
                List.of(deploymentId, id),
                "Deployment request",
                deploymentRequestModelMapper::execute);
    }
}
