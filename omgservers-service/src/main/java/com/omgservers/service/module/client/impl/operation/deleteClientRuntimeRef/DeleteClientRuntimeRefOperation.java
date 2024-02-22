package com.omgservers.service.module.client.impl.operation.deleteClientRuntimeRef;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteClientRuntimeRefOperation {
    Uni<Boolean> deleteClientRuntimeRef(ChangeContext<?> changeContext,
                                        SqlConnection sqlConnection,
                                        int shard,
                                        Long clientId,
                                        Long id);
}
