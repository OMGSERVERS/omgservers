package com.omgservers.module.tenant.impl.operation.selectVersionRuntimesIds;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectVersionRuntimeIdsOperation {
    Uni<List<Long>> selectVersionRuntimeIds(SqlConnection sqlConnection,
                                            int shard,
                                            Long tenantId,
                                            Long versionId,
                                            Boolean deleted);
}
