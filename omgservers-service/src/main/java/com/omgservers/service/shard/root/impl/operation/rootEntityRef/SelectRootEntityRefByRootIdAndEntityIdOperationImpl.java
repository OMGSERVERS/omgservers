package com.omgservers.service.shard.root.impl.operation.rootEntityRef;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.service.shard.root.impl.mappers.RootEntityRefModelMapper;
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
class SelectRootEntityRefByRootIdAndEntityIdOperationImpl
        implements SelectRootEntityRefByRootIdAndEntityIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RootEntityRefModelMapper rootEntityRefModelMapper;

    @Override
    public Uni<RootEntityRefModel> execute(final SqlConnection sqlConnection,
                                           final int slot,
                                           final Long rootId,
                                           final Long entityId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, root_id, created, modified, qualifier, entity_id, deleted
                        from $slot.tab_root_entity_ref
                        where root_id = $1 and entity_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(rootId, entityId),
                "Root entity ref",
                rootEntityRefModelMapper::execute);
    }
}
