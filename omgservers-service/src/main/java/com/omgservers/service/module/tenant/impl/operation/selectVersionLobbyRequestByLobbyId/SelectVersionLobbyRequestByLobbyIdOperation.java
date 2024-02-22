package com.omgservers.service.module.tenant.impl.operation.selectVersionLobbyRequestByLobbyId;

import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionLobbyRequestByLobbyIdOperation {
    Uni<VersionLobbyRequestModel> selectVersionLobbyRequestByLobbyId(SqlConnection sqlConnection,
                                                                     int shard,
                                                                     Long tenantId,
                                                                     Long versionId,
                                                                     Long lobbyId);
}
