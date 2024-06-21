package com.omgservers.service.module.tenant.impl.operation.versionImageRef.upsertVersionImageRef;

import com.omgservers.model.versionImageRef.VersionImageRefModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionImageRefOperation {
    Uni<Boolean> upsertVersionImageRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       VersionImageRefModel versionImageRef);
}
