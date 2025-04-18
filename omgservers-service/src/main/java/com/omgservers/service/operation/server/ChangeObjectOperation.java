package com.omgservers.service.operation.server;

import com.omgservers.schema.model.log.LogModel;
import com.omgservers.service.event.EventBodyModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;
import java.util.function.Supplier;

public interface ChangeObjectOperation {

    Uni<Boolean> execute(final ChangeContext<?> changeContext,
                         final SqlConnection sqlConnection,
                         final int slot,
                         String sql,
                         List<?> parameters,
                         Supplier<EventBodyModel> eventBodySupplier,
                         Supplier<LogModel> logSupplier);

    Uni<Boolean> execute(final ChangeContext<?> changeContext,
                         final SqlConnection sqlConnection,
                         String sql,
                         List<?> parameters,
                         Supplier<EventBodyModel> eventBodySupplier,
                         Supplier<LogModel> logSupplier);
}
