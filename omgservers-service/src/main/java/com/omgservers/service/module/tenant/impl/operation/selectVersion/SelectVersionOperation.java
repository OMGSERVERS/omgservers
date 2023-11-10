package com.omgservers.service.module.tenant.impl.operation.selectVersion;

import com.omgservers.model.version.VersionModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionOperation {
    Uni<VersionModel> selectVersion(SqlConnection sqlConnection,
                                    int shard,
                                    Long tenantId,
                                    Long id);
}
