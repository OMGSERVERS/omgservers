package com.omgservers.service.shard.root.impl.operation.root;

import com.omgservers.schema.model.root.RootModel;
import com.omgservers.service.shard.root.impl.mappers.RootModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRootOperationImpl implements SelectRootOperation {

    final SelectObjectOperation selectObjectOperation;

    final RootModelMapper rootModelMapper;

    @Override
    public Uni<RootModel> execute(final SqlConnection sqlConnection,
                                  final int shard,
                                  final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, deleted
                        from $shard.tab_root
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Root",
                rootModelMapper::execute);
    }
}
