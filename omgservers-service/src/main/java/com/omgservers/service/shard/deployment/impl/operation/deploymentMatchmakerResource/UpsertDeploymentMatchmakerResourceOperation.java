package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertDeploymentMatchmakerResourceOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         DeploymentMatchmakerResourceModel deploymentMatchmakerResource);
}
