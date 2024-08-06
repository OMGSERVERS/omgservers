package com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.upsertVersionLobbyRequest;

import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionLobbyRequestOperation {
    Uni<Boolean> upsertVersionLobbyRequest(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           VersionLobbyRequestModel versionLobbyRequest);
}
