package com.omgservers.service.master.node.impl.operation;

import com.omgservers.schema.model.node.NodeModel;
import com.omgservers.service.master.node.impl.mapper.NodeModelMapper;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class AcquireNodeOperationImpl implements AcquireNodeOperation {

    final SelectObjectOperation selectObjectOperation;

    final NodeModelMapper nodeModelMapper;

    @Override
    public Uni<NodeModel> execute(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection) {
        return selectObjectOperation.selectObject(sqlConnection,
                """
                        update $master.tab_node
                        set modified = $1, deleted = true
                        where id = (
                            select id
                            from $master.tab_node
                            where deleted = false
                            order by id
                            limit 1
                            for update skip locked
                        )
                        returning id, modified, deleted
                        """,
                List.of(Instant.now().atOffset(ZoneOffset.UTC)),
                "Node",
                nodeModelMapper::execute);
    }
}
