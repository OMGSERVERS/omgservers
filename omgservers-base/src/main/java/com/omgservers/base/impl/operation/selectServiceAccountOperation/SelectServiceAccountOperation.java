package com.omgservers.base.impl.operation.selectServiceAccountOperation;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectServiceAccountOperation {
    Uni<ServiceAccountModel> selectServiceAccount(SqlConnection sqlConnection, String username);
}
