package com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectActiveVersionImageRefsByTenantId;

import com.omgservers.schema.model.versionImageRef.VersionImageRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveVersionImageRefsByTenantId {
    Uni<List<VersionImageRefModel>> selectActiveVersionImageRefsByTenantId(SqlConnection sqlConnection,
                                                                           int shard,
                                                                           Long tenantId);
}
