package com.omgservers.service.module.system.impl.operation.upsertEntity;

import com.omgservers.model.entitiy.EntityModel;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertEntityOperationImpl implements UpsertEntityOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> upsertEntity(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final EntityModel entity) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        insert into system.tab_entity(
                            id, created, modified, entity_id, qualifier, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        entity.getId(),
                        entity.getCreated().atOffset(ZoneOffset.UTC),
                        entity.getModified().atOffset(ZoneOffset.UTC),
                        entity.getEntityId(),
                        entity.getQualifier(),
                        entity.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }
}
