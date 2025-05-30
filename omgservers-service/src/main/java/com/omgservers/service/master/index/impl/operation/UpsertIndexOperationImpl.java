package com.omgservers.service.master.index.impl.operation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.index.IndexModel;
import com.omgservers.service.event.body.system.IndexCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
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

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertIndex(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final IndexModel index) {
        return changeObjectOperation.execute(
                changeContext,
                sqlConnection,
                """
                        insert into $master.tab_index(
                            id, idempotency_key, created, modified, config, deleted)
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
                IndexCreatedEventBodyModel::new,
                () -> null
        );
    }

    String getConfigString(final IndexModel index) {
        try {
            return objectMapper.writeValueAsString(index.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
