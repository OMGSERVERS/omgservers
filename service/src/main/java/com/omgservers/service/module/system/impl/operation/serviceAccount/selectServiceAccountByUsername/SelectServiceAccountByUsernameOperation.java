package com.omgservers.service.module.system.impl.operation.serviceAccount.selectServiceAccountByUsername;

import com.omgservers.model.serviceAccount.ServiceAccountModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectServiceAccountByUsernameOperation {
    Uni<ServiceAccountModel> selectServiceAccountByUsername(SqlConnection sqlConnection,
                                                            String username);
}
