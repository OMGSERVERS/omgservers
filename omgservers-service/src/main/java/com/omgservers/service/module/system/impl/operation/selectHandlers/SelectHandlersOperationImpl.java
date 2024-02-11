package com.omgservers.service.module.system.impl.operation.selectHandlers;

import com.omgservers.model.handler.HandlerModel;
import com.omgservers.service.module.system.impl.mappers.HandlerModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectHandlersOperationImpl implements SelectHandlersOperation {

    final SelectListOperation selectListOperation;
    final HandlerModelMapper handlerModelMapper;

    public Uni<List<HandlerModel>> selectHandlers(final SqlConnection sqlConnection) {
        return selectListOperation.selectList(
                sqlConnection,
                0,
                """
                        select id, created, modified, deleted
                        from system.tab_handler
                        limit 1
                        """,
                List.of(),
                "Handler",
                handlerModelMapper::fromRow);
    }
}
