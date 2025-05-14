package com.omgservers.service.master.task.impl.operation;

import com.omgservers.schema.model.task.TaskModel;
import com.omgservers.service.master.task.impl.mapper.TaskModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTaskOperationImpl implements SelectTaskOperation {

    final SelectObjectOperation selectObjectOperation;

    final TaskModelMapper taskModelMapper;

    @Override
    public Uni<TaskModel> execute(final SqlConnection sqlConnection,
                                  final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                """
                        select
                            id, idempotency_key, created, modified, qualifier, shard_key, entity_id, deleted
                        from $master.tab_task
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Task",
                taskModelMapper::execute);
    }
}
