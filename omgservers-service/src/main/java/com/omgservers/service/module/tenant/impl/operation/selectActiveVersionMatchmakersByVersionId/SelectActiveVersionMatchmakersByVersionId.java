package com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakersByVersionId;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionMatchmakersByVersionId {
    Uni<List<VersionMatchmakerModel>> selectActiveVersionMatchmakersByVersionId(SqlConnection sqlConnection,
                                                                                int shard,
                                                                                Long tenantId,
                                                                                Long versionId);
}
