package com.omgservers.service.service.index.operation.getIndex;

import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
import com.omgservers.service.service.index.impl.mapper.IndexModelMapper;
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
                0,
                """
                        select id, idempotency_key, created, modified, config, deleted
                        from system.tab_index
                        limit 1
                        """,
                new ArrayList<>(),
                "Index",
                indexModelMapper::fromRow);
    }
}
