package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.selectActiveVersionMatchmakerRefsByVersionId;

import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionMatchmakerRefsByVersionId {
    Uni<List<VersionMatchmakerRefModel>> selectActiveVersionMatchmakerRefsByVersionId(SqlConnection sqlConnection,
                                                                                      int shard,
                                                                                      Long tenantId,
                                                                                      Long versionId);
}
