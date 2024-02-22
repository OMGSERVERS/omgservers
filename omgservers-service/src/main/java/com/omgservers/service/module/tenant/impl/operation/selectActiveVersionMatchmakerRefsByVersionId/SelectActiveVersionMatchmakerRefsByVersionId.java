package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakerRefsByVersionId;

import com.omgservers.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionMatchmakerRefsByVersionId {
    Uni<List<VersionMatchmakerRefModel>> selectActiveVersionMatchmakerRefsByVersionId(SqlConnection sqlConnection,
                                                                                      int shard,
                                                                                      Long tenantId,
                                                                                      Long versionId);
}
