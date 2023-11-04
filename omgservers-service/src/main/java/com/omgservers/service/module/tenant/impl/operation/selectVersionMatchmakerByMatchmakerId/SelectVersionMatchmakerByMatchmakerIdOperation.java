package com.omgservers.service.module.tenant.impl.operation.selectVersionMatchmakerByMatchmakerId;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionMatchmakerByMatchmakerIdOperation {
    Uni<VersionMatchmakerModel> selectVersionMatchmakerByMatchmakerId(SqlConnection sqlConnection,
                                                                      int shard,
                                                                      Long tenantId,
                                                                      Long matchmakerId);
}
