package com.omgservers.service.module.tenant.impl.operation.upsertVersionLobbyRequest;

import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertVersionLobbyRequestOperation {
    Uni<Boolean> upsertVersionLobbyRequest(ChangeContext<?> changeContext,
                                           SqlConnection sqlConnection,
                                           int shard,
                                           VersionLobbyRequestModel versionLobbyRequest);
}
