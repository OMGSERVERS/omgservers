package com.omgservers.service.master.task.impl.operation;

import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.master.task.impl.mapper.TaskModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTasksOperationImpl implements SelectTasksOperation {

    final SelectListOperation selectListOperation;

    final TaskModelMapper taskModelMapper;

    @Override
    public Uni<List<TaskModel>> execute(final SqlConnection sqlConnection) {
        return selectListOperation.selectList(
                sqlConnection,
                """
                        select
                            id, idempotency_key, created, modified, qualifier, shard_key, entity_id, deleted
                        from $master.tab_task
                        where deleted = false
                        order by id asc
                        """,
                List.of(),
                "Task",
                taskModelMapper::execute);
    }
}
