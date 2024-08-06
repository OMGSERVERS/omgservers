package com.omgservers.service.module.tenant.impl.operation.versionImageRef.deleteVersionImageRef;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionImageRefOperation {
    Uni<Boolean> deleteVersionImageRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       Long tenantId,
                                       Long id);
}
