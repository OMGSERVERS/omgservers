package com.omgservers.base.impl.operation.upsertServiceAccountOperation;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertServiceAccountOperation {
    Uni<Void> upsertServiceAccount(SqlConnection sqlConnection, ServiceAccountModel serviceAccount);
}
