package com.omgservers.service.shard.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantImageOperation {
    Uni<TenantImageModel> execute(SqlConnection sqlConnection,
                                  int slot,
                                  Long tenantId,
                                  Long id);
}
