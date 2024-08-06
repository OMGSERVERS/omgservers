package com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.selectVersionLobbyRequest;

import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionLobbyRequestOperation {
    Uni<VersionLobbyRequestModel> selectVersionLobbyRequest(SqlConnection sqlConnection,
                                                            int shard,
                                                            Long tenantId,
                                                            Long id);
}
