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
class SelectEntityOperationImpl implements SelectEntityOperation {

    final SelectObjectOperation selectObjectOperation;

    final EntityModelMapper entityModelMapper;

    @Override
    public Uni<EntityModel> execute(final SqlConnection sqlConnection,
                                    final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                """
                        select id, idempotency_key, created, modified, qualifier, entity_id, deleted
                        from $master.tab_entity
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Entity",
                entityModelMapper::execute);
    }
}
