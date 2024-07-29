package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.deleteVersionMatchmakerRequest;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionMatchmakerRequestOperation {
    Uni<Boolean> deleteVersionMatchmakerRequest(ChangeContext<?> changeContext,
                                                SqlConnection sqlConnection,
                                                int shard,
                                                Long tenantId,
                                                Long id);
}
