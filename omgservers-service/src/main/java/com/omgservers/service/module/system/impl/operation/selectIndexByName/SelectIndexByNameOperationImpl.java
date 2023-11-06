package com.omgservers.service.module.system.impl.operation.selectIndexByName;

import com.omgservers.model.index.IndexModel;
import com.omgservers.service.module.system.impl.mappers.IndexModelMapper;
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
class SelectIndexByNameOperationImpl implements SelectIndexByNameOperation {

    final SelectObjectOperation selectObjectOperation;

    final IndexModelMapper indexModelMapper;

    @Override
    public Uni<IndexModel> selectIndexByName(final SqlConnection sqlConnection,
                                             final String name) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, name, config, deleted
                        from system.tab_index
                        where name = $1 and deleted = false
                        limit 1
                        """,
                Collections.singletonList(name),
                "Index",
                indexModelMapper::fromRow);
    }
}
