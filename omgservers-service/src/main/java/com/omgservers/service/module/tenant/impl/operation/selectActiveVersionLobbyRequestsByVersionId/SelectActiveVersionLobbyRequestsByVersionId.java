package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionLobbyRequestsByVersionId;

import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionLobbyRequestsByVersionId {
    Uni<List<VersionLobbyRequestModel>> selectActiveVersionLobbyRequestsByVersionId(SqlConnection sqlConnection,
                                                                                    int shard,
                                                                                    Long tenantId,
                                                                                    Long versionId);
}
