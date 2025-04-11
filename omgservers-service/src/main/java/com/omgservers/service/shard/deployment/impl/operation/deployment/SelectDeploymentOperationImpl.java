package com.omgservers.service.shard.deployment.impl.operation.deployment;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.shard.deployment.impl.mapper.DeploymentModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectDeploymentOperationImpl implements SelectDeploymentOperation {

    final SelectObjectOperation selectObjectOperation;

    final DeploymentModelMapper deploymentModelMapper;

    @Override
    public Uni<DeploymentModel> execute(final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, created, modified, tenant_id, stage_id, version_id, config, deleted
                        from $shard.tab_deployment
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Deployment",
                deploymentModelMapper::execute);
    }
}
