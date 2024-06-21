package com.omgservers.service.module.tenant.impl.operation.versionImageRef.selectVersionImageRef;

import com.omgservers.model.versionImageRef.VersionImageRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectVersionImageRefOperation {
    Uni<VersionImageRefModel> selectVersionImageRef(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long tenantId,
                                                    Long id);
}
