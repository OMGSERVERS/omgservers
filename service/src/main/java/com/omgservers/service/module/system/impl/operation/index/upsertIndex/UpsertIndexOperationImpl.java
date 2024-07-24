package com.omgservers.service.module.system.impl.operation.index.upsertIndex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.model.index.IndexModel;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertIndexOperationImpl implements UpsertIndexOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertIndex(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final IndexModel index) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        insert into system.tab_index(id, idempotency_key, created, modified, config, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        index.getId(),
                        index.getIdempotencyKey(),
                        index.getCreated().atOffset(ZoneOffset.UTC),
                        index.getModified().atOffset(ZoneOffset.UTC),
                        getConfigString(index),
                        index.getDeleted()
                ),
                () -> new IndexCreatedEventBodyModel(index.getId()),
                () -> null
        );
    }

    String getConfigString(IndexModel index) {
        try {
            return objectMapper.writeValueAsString(index.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
