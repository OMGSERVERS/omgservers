package com.omgservers.module.system.impl.operation.deleteContainer;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteContainerOperation {
    Uni<Boolean> deleteContainer(ChangeContext<?> changeContext,
                                 SqlConnection sqlConnection,
                                 Long id);
}
