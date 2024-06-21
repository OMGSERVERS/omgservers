package com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.selectActiveVersionLobbyRequestsByTenantId;

import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionLobbyRequestsByTenantId {
    Uni<List<VersionLobbyRequestModel>> selectActiveVersionLobbyRequestsByTenantId(SqlConnection sqlConnection,
                                                                                   int shard,
                                                                                   Long tenantId);
}
