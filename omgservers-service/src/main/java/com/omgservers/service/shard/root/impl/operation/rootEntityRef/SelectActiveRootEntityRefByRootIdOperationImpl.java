package com.omgservers.service.shard.root.impl.operation.rootEntityRef;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.service.shard.root.impl.mappers.RootEntityRefModelMapper;
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
class SelectActiveRootEntityRefByRootIdOperationImpl
        implements SelectActiveRootEntityRefByRootIdOperation {

    final SelectListOperation selectListOperation;

    final RootEntityRefModelMapper rootEntityRefModelMapper;

    @Override
    public Uni<List<RootEntityRefModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long rootId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, root_id, created, modified, qualifier, entity_id, deleted
                        from $shard.tab_root_entity_ref
                        where root_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(rootId),
                "Root entity ref",
                rootEntityRefModelMapper::execute);
    }
}
