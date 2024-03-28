package com.omgservers.service.module.root.impl.operation.upsertRoot;

import com.omgservers.model.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.model.root.RootModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRootOperationImpl implements UpsertRootOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertRoot(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final RootModel root) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_root(
                            id, idempotency_key, created, modified, default_pool_id, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        root.getId(),
                        root.getIdempotencyKey(),
                        root.getCreated().atOffset(ZoneOffset.UTC),
                        root.getModified().atOffset(ZoneOffset.UTC),
                        root.getDefaultPoolId(),
                        root.getDeleted()
                ),
                () -> new RootCreatedEventBodyModel(root.getId()),
                () -> null
        );
    }
}
