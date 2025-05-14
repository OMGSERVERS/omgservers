package com.omgservers.service.master.entity.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.entity.EntityModel;
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
class UpsertEntityOperationImpl implements UpsertEntityOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final EntityModel entity) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection,
                """
                        insert into $master.tab_entity(
                            id, idempotency_key, created, modified, qualifier, entity_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        entity.getId(),
                        entity.getIdempotencyKey(),
                        entity.getCreated().atOffset(ZoneOffset.UTC),
                        entity.getModified().atOffset(ZoneOffset.UTC),
                        entity.getQualifier(),
                        entity.getEntityId(),
                        entity.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
