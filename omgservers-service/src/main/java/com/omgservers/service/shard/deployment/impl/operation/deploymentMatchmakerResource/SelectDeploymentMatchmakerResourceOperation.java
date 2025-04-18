package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectDeploymentMatchmakerResourceOperation {
    Uni<DeploymentMatchmakerResourceModel> execute(SqlConnection sqlConnection,
                                                   int slot,
                                                   Long deploymentId,
                                                   Long id);
}
