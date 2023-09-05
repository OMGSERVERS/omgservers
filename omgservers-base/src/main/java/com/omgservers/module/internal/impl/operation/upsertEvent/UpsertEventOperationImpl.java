package com.omgservers.module.internal.impl.operation.upsertEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.EventModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertEventOperationImpl implements UpsertEventOperation {

    static private final String sql = """
            insert into internal.tab_event(id, created, modified, group_id, qualifier, body, status)
            values($1, $2, $3, $4, $5, $6, $7)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertEvent(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final EventModel event) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (event == null) {
            throw new ServerSideBadRequestException("event is null");
        }

        return upsertObject(sqlConnection, event)
                .invoke(eventWasInserted -> {
                    if (eventWasInserted) {
                        changeContext.add(event);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(final SqlConnection sqlConnection, final EventModel event) {
        try {
            var bodyString = objectMapper.writeValueAsString(event.getBody());
            return sqlConnection.preparedQuery(sql)
                    .execute(Tuple.from(Arrays.asList(
                            event.getId(),
                            event.getCreated().atOffset(ZoneOffset.UTC),
                            event.getModified().atOffset(ZoneOffset.UTC),
                            event.getGroupId(),
                            event.getQualifier(),
                            bodyString,
                            event.getStatus()
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
