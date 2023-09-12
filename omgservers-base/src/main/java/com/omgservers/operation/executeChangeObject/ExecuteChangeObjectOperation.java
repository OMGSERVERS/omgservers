package com.omgservers.operation.executeChangeObject;

import com.omgservers.model.event.EventBodyModel;
import com.omgservers.model.log.LogModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;
import java.util.function.Supplier;

public interface ExecuteChangeObjectOperation {

    Uni<Boolean> executeChangeObject(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     String sql,
                                     List<?> parameters,
                                     Supplier<EventBodyModel> eventBodySupplier,
                                     Supplier<LogModel> logSupplier);
}