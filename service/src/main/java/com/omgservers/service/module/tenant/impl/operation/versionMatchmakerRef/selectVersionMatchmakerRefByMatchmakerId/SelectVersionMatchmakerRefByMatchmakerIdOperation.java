package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectVersionMatchmakerRefByMatchmakerId;

import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionMatchmakerRefByMatchmakerIdOperation {
    Uni<VersionMatchmakerRefModel> selectVersionMatchmakerRefByMatchmakerId(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long tenantId,
                                                                            Long versionId,
                                                                            Long matchmakerId);
}
