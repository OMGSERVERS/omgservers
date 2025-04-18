package com.omgservers.service.shard.runtime.impl.operation.runtimeMessage;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.runtime.impl.mapper.RuntimeMessageModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveRuntimeMessagesByRuntimeIdOperationImpl
        implements SelectActiveRuntimeMessagesByRuntimeIdOperation {

    final SelectListOperation selectListOperation;

    final RuntimeMessageModelMapper runtimeMessageModelMapper;

    @Override
    public Uni<List<RuntimeMessageModel>> execute(final SqlConnection sqlConnection,
                                                  final int slot,
                                                  final Long runtimeId) {
        return selectListOperation.selectList(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, runtime_id, created, modified, qualifier, body, deleted
                        from $slot.tab_runtime_message
                        where runtime_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(runtimeId),
                "Runtime message",
                runtimeMessageModelMapper::execute);
    }
}
