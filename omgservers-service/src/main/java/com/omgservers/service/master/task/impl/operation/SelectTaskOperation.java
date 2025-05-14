package com.omgservers.service.master.task.impl.operation;

import com.omgservers.schema.model.task.TaskModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTaskOperation {
    Uni<TaskModel> execute(SqlConnection sqlConnection, Long id);
}
