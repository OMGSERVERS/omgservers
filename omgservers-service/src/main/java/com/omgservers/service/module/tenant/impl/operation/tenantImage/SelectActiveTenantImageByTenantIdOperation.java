package com.omgservers.service.module.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantImageByTenantIdOperation {
    Uni<List<TenantImageModel>> execute(SqlConnection sqlConnection,
                                        int shard,
                                        Long tenantId);
}
