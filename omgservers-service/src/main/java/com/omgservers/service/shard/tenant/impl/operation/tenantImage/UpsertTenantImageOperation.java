package com.omgservers.service.shard.tenant.impl.operation.tenantImage;

import com.omgservers.schema.model.tenantImage.TenantImageModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantImageOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         TenantImageModel tenantImage);
}
