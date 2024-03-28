package com.omgservers.service.module.root.impl.operation.selectRoot;

import com.omgservers.model.root.RootModel;
import com.omgservers.service.module.root.impl.mappers.RootModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
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
    public Uni<RootModel> selectRoot(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, default_pool_id, deleted
                        from $schema.tab_root
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Root",
                rootModelMapper::fromRow);
    }
}
