package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectVersionMatchmakerRef;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionMatchmakerRefOperation {
    Uni<VersionMatchmakerRefModel> selectVersionMatchmakerRef(SqlConnection sqlConnection,
                                                              int shard,
                                                              Long tenantId,
                                                              Long id);
}
