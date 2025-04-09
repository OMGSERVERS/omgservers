package com.omgservers.service.shard.root.impl.operation.root;

import com.omgservers.schema.model.root.RootModel;
import com.omgservers.service.event.body.module.root.RootCreatedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
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
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final RootModel root) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, shard,
                """
                        insert into $shard.tab_root(
                            id, idempotency_key, created, modified, deleted)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        root.getId(),
                        root.getIdempotencyKey(),
                        root.getCreated().atOffset(ZoneOffset.UTC),
                        root.getModified().atOffset(ZoneOffset.UTC),
                        root.getDeleted()
                ),
                () -> new RootCreatedEventBodyModel(root.getId()),
                () -> null
        );
    }
}
