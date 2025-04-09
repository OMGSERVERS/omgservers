package com.omgservers.service.server.index.operation;

import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.server.index.impl.mapper.IndexModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIndexOperationImpl implements GetIndexOperation {

    final SelectObjectOperation selectObjectOperation;

    final IndexModelMapper indexModelMapper;

    @Override
    public Uni<IndexModel> getIndex(final SqlConnection sqlConnection) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                """
                        select id, idempotency_key, created, modified, config, deleted
                        from $server.tab_index
                        order by id desc
                        limit 1
                        """,
                new ArrayList<>(),
                "Index",
                indexModelMapper::execute);
    }
}
