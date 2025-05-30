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
class SelectDeploymentRequestByClientIdOperationImpl
        implements SelectDeploymentRequestByClientIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final DeploymentRequestModelMapper deploymentRequestModelMapper;

    @Override
    public Uni<DeploymentRequestModel> execute(final SqlConnection sqlConnection,
                                               final int slot,
                                               final Long deploymentId,
                                               final Long clientId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, client_id, deleted
                        from $slot.tab_deployment_request
                        where deployment_id = $1 and client_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(deploymentId, clientId),
                "Deployment request",
                deploymentRequestModelMapper::execute);
    }
}
