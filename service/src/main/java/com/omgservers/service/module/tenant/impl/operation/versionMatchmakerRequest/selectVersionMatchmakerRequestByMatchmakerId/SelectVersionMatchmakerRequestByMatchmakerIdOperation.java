package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.selectVersionMatchmakerRequestByMatchmakerId;

import com.omgservers.model.versionMatchmakerRequest.VersionMatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionMatchmakerRequestByMatchmakerIdOperation {
    Uni<VersionMatchmakerRequestModel> selectVersionMatchmakerRequestByMatchmakerId(SqlConnection sqlConnection,
                                                                                    int shard,
                                                                                    Long tenantId,
                                                                                    Long versionId,
                                                                                    Long matchmakerId);
}
