package com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByVersionId;

import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionImageRefsByVersionId {
    Uni<List<VersionImageRefModel>> selectActiveVersionImageRefsByVersionId(SqlConnection sqlConnection,
                                                                            int shard,
                                                                            Long tenantId,
                                                                            Long versionId);
}
