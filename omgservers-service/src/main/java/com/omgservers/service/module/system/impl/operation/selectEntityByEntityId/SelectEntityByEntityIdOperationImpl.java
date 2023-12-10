package com.omgservers.service.module.system.impl.operation.selectEntityByEntityId;

import com.omgservers.model.entitiy.EntityModel;
import com.omgservers.service.module.system.impl.mappers.EntityModelMapper;
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
class SelectEntityByEntityIdOperationImpl implements SelectEntityByEntityIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final EntityModelMapper entityModelMapper;

    @Override
    public Uni<EntityModel> selectEntityByEntityId(final SqlConnection sqlConnection,
                                                   final Long entityId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, entity_id, qualifier, deleted
                        from system.tab_entity
                        where entity_id = $1
                        limit 1
                        """,
                Collections.singletonList(entityId),
                "Entity",
                entityModelMapper::fromRow);
    }
}
