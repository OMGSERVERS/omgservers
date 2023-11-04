package com.omgservers.service.module.system.impl.operation.deleteServiceAccount;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteServiceAccountOperation {
    Uni<Boolean> deleteServiceAccount(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      String username);
}
