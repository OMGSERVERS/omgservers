package com.omgservers.module.internal.impl.operation.upsertEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.EventModel;
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
import java.util.ArrayList;

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

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertEvent(final SqlConnection sqlConnection, final EventModel event) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (event == null) {
            throw new ServerSideBadRequestException("event is null");
        }

        return upsertQuery(sqlConnection, event)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Event was inserted, event={}", event);
                    } else {
                        log.info("Event was updated, event={}", event);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertQuery(final SqlConnection sqlConnection, final EventModel event) {
        try {
            var bodyString = objectMapper.writeValueAsString(event.getBody());
            return sqlConnection.preparedQuery(sql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(event.getId());
                        add(event.getCreated().atOffset(ZoneOffset.UTC));
                        add(event.getModified().atOffset(ZoneOffset.UTC));
                        add(event.getGroupId());
                        add(event.getQualifier());
                        add(bodyString);
                        add(event.getStatus());
                    }}))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
