package com.omgservers.service.module.system.impl.operation.selectHandler;

import com.omgservers.model.handler.HandlerModel;
import com.omgservers.service.module.system.impl.mappers.HandlerModelMapper;
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
class SelectHandlerOperationImpl implements SelectHandlerOperation {

    final SelectObjectOperation selectObjectOperation;
    final HandlerModelMapper handlerModelMapper;

    @Override
    public Uni<HandlerModel> selectHandler(final SqlConnection sqlConnection,
                                           final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, deleted
                        from system.tab_handler
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Handler",
                handlerModelMapper::fromRow);
    }
}
