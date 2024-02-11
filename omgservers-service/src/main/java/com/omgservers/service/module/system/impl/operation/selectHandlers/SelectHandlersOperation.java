package com.omgservers.service.module.system.impl.operation.selectHandlers;

import com.omgservers.model.handler.HandlerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectHandlersOperation {
    Uni<List<HandlerModel>> selectHandlers(SqlConnection sqlConnection);
}
