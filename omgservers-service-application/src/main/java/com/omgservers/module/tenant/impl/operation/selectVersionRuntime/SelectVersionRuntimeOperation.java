package com.omgservers.module.tenant.impl.operation.selectVersionRuntime;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionRuntimeOperation {
    Uni<VersionRuntimeModel> selectVersionRuntime(SqlConnection sqlConnection,
                                                  int shard,
                                                  Long tenantId,
                                                  Long id);
}
