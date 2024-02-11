package com.omgservers.service.module.system.impl.operation.selectHandler;

import com.omgservers.model.handler.HandlerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectHandlerOperation {
    Uni<HandlerModel> selectHandler(SqlConnection sqlConnection, Long id);
}
