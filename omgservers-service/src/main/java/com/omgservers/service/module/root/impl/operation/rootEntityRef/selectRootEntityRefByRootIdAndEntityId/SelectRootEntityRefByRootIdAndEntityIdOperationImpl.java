package com.omgservers.service.module.root.impl.operation.rootEntityRef.selectRootEntityRefByRootIdAndEntityId;

import com.omgservers.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.service.module.root.impl.mappers.RootEntityRefModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRootEntityRefByRootIdAndEntityIdOperationImpl
        implements SelectRootEntityRefByRootIdAndEntityIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RootEntityRefModelMapper rootEntityRefModelMapper;

    @Override
    public Uni<RootEntityRefModel> selectRootEntityRefByRootIdAndEntityId(
            final SqlConnection sqlConnection,
            final int shard,
            final Long rootId,
            final Long entityId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, root_id, created, modified, qualifier, entity_id, deleted
                        from $schema.tab_root_entity_ref
                        where root_id = $1 and entity_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(rootId, entityId),
                "Root entity ref",
                rootEntityRefModelMapper::fromRow);
    }
}
