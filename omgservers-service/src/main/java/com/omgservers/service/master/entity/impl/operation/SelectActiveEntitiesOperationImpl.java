package com.omgservers.service.master.entity.impl.operation;

import com.omgservers.schema.model.entity.EntityModel;
import com.omgservers.service.master.entity.impl.mappers.EntityModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveEntitiesOperationImpl
        implements SelectActiveEntitiesOperation {

    final SelectListOperation selectListOperation;

    final EntityModelMapper entityModelMapper;

    @Override
    public Uni<List<EntityModel>> execute(final SqlConnection sqlConnection) {
        return selectListOperation.selectList(
                sqlConnection,
                """
                        select id, idempotency_key, created, modified, qualifier, entity_id, deleted
                        from $master.tab_entity
                        where deleted = false
                        order by id asc
                        """,
                List.of(),
                "Entity",
                entityModelMapper::execute);
    }
}
