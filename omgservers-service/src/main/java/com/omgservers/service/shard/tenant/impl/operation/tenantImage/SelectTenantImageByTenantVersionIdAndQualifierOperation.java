package com.omgservers.service.shard.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantImageByTenantVersionIdAndQualifierOperation {
    Uni<TenantImageModel> execute(SqlConnection sqlConnection,
                                  int slot,
                                  Long tenantId,
                                  Long tenantVersionId,
                                  TenantImageQualifierEnum qualifier);
}
