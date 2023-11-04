package com.omgservers.service.module.system.impl.operation.deleteContainer;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteContainerOperation {
    Uni<Boolean> deleteContainer(ChangeContext<?> changeContext,
                                 SqlConnection sqlConnection,
                                 Long id);
}
