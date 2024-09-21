package com.omgservers.service.module.tenant.impl.operation.tenantImageRef;

import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantImageRefsByTenantVersionIdAndQualifierOperation {
    Uni<TenantImageRefModel> execute(
            SqlConnection sqlConnection,
            int shard,
            Long tenantId,
            Long tenantVersionId,
            TenantImageRefQualifierEnum qualifier);
}
