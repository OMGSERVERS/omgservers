package com.omgservers.service.module.root.impl.operation.rootEntityRef.upsertRootEntityRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
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
class UpsertRootEntityRefOperationImpl implements UpsertRootEntityRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRootEntityRef(final ChangeContext<?> changeContext,
                                            final SqlConnection sqlConnection,
                                            final int shard,
                                            final RootEntityRefModel rootEntityRefModel) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_root_entity_ref(
                            id, idempotency_key, root_id, created, modified, qualifier, entity_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        rootEntityRefModel.getId(),
                        rootEntityRefModel.getIdempotencyKey(),
                        rootEntityRefModel.getRootId(),
                        rootEntityRefModel.getCreated().atOffset(ZoneOffset.UTC),
                        rootEntityRefModel.getModified().atOffset(ZoneOffset.UTC),
                        rootEntityRefModel.getQualifier(),
                        rootEntityRefModel.getEntityId(),
                        rootEntityRefModel.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
