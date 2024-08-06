package com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectVersionLobbyRef;

import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionLobbyRefOperation {
    Uni<VersionLobbyRefModel> selectVersionLobbyRef(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long tenantId,
                                                    Long id);
}
