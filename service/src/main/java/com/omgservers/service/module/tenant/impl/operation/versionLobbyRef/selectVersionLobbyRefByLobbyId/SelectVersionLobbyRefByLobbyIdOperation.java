package com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectVersionLobbyRefByLobbyId;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionLobbyRefByLobbyIdOperation {
    Uni<VersionLobbyRefModel> selectVersionLobbyRefByLobbyId(SqlConnection sqlConnection,
                                                             int shard,
                                                             Long tenantId,
                                                             Long versionId,
                                                             Long lobbyId);
}
