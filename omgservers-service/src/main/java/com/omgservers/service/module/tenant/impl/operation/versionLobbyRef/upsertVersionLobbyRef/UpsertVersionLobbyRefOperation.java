package com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.upsertVersionLobbyRef;

import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionLobbyRefOperation {
    Uni<Boolean> upsertVersionLobbyRef(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       VersionLobbyRefModel versionLobbyRef);
}
