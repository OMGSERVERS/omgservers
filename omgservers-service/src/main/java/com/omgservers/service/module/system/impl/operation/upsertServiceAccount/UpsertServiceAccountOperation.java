package com.omgservers.service.module.system.impl.operation.upsertServiceAccount;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertServiceAccountOperation {
    Uni<Boolean> upsertServiceAccount(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      ServiceAccountModel serviceAccount);
}
