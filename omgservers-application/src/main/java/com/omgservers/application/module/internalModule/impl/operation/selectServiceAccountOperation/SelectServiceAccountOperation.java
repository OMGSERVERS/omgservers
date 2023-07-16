package com.omgservers.application.module.internalModule.impl.operation.selectServiceAccountOperation;

import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectServiceAccountOperation {
    Uni<ServiceAccountModel> selectServiceAccount(SqlConnection sqlConnection, String username);
}
