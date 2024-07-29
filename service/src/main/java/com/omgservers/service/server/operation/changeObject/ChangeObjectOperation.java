package com.omgservers.service.server.operation.changeObject;

import com.omgservers.schema.event.EventBodyModel;
import com.omgservers.schema.model.log.LogModel;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;
import java.util.function.Supplier;

public interface ChangeObjectOperation {

    Uni<Boolean> changeObject(final ChangeContext<?> changeContext,
                              final SqlConnection sqlConnection,
                              final int shard,
                              String sql,
                              List<?> parameters,
                              Supplier<EventBodyModel> eventBodySupplier,
                              Supplier<LogModel> logSupplier);
}
