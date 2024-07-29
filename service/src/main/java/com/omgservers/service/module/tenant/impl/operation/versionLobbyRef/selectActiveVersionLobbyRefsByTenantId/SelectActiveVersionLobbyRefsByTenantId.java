package com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectActiveVersionLobbyRefsByTenantId;

import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionLobbyRefsByTenantId {
    Uni<List<VersionLobbyRefModel>> selectActiveVersionLobbyRefsByTenantId(SqlConnection sqlConnection,
                                                                           int shard,
                                                                           Long tenantId);
}
