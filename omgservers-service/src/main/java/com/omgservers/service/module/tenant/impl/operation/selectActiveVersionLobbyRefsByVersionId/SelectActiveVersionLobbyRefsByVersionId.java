package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionLobbyRefsByVersionId;

import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionLobbyRefsByVersionId {
    Uni<List<VersionLobbyRefModel>> selectActiveVersionLobbyRefsByVersionId(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long tenantId,
                                                                            Long versionId);
}
