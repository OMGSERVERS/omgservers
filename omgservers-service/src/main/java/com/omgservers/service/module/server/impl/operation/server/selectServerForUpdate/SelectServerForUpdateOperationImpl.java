package com.omgservers.service.module.server.impl.operation.server.selectServerForUpdate;

import com.omgservers.model.server.ServerModel;
import com.omgservers.service.module.server.impl.mapper.ServerModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectServerForUpdateOperationImpl implements SelectServerForUpdateOperation {

    final SelectObjectOperation selectObjectOperation;

    final ServerModelMapper serverModelMapper;

    @Override
    public Uni<ServerModel> selectServerForUpdate(final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, created, modified, pool_id, qualifier, uri, cpu_count, cpu_used,
                            memory_size, memory_used, config, deleted
                        from $schema.tab_server
                        where id = $1
                        limit 1
                        for update
                        """,
                Collections.singletonList(id),
                "Server",
                serverModelMapper::fromRow);
    }
}
