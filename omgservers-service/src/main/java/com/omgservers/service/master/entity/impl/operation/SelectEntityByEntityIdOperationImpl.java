package com.omgservers.service.master.entity.impl.operation;

import com.omgservers.schema.model.entity.EntityModel;
import com.omgservers.service.master.entity.impl.mappers.EntityModelMapper;
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
class SelectEntityByEntityIdOperationImpl
        implements SelectEntityByEntityIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final EntityModelMapper entityModelMapper;

    @Override
    public Uni<EntityModel> execute(final SqlConnection sqlConnection,
                                    final Long entityId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                """
                        select id, idempotency_key, created, modified, qualifier, entity_id, deleted
                        from $master.tab_entity
                        where entity_id = $1
                        order by id desc
                        limit 1
                        """,
                List.of(entityId),
                "Entity",
                entityModelMapper::execute);
    }
}
