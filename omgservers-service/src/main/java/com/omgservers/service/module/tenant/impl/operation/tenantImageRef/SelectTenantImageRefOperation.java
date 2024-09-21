package com.omgservers.service.module.tenant.impl.operation.tenantImageRef;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantImageRefOperation {
    Uni<TenantImageRefModel> execute(SqlConnection sqlConnection,
                                     int shard,
                                     Long tenantId,
                                     Long id);
}
