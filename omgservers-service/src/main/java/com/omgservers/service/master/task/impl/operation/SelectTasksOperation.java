package com.omgservers.service.master.task.impl.operation;

import com.omgservers.schema.model.task.TaskModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectTasksOperation {
    Uni<List<TaskModel>> execute(SqlConnection sqlConnection);
}
