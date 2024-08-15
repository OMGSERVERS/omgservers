package com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.deleteVersionJenkinsRequest;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionJenkinsRequestOperation {
    Uni<Boolean> deleteVersionJenkinsRequest(ChangeContext<?> changeContext,
                                             SqlConnection sqlConnection,
                                             int shard,
                                             Long tenantId,
                                             Long id);
}
