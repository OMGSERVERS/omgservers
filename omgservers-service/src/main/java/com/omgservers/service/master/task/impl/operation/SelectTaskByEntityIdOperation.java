package com.omgservers.service.master.task.impl.operation;

import com.omgservers.schema.model.task.TaskModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTaskByEntityIdOperation {
    Uni<TaskModel> execute(SqlConnection sqlConnection,
                           Long shardKey,
                           Long entityId);
}
