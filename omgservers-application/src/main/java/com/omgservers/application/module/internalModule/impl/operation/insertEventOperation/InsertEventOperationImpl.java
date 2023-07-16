package com.omgservers.application.module.internalModule.impl.operation.insertEventOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.exception.ServerSideInternalException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InsertEventOperationImpl implements InsertEventOperation {

    static private final String sql = """
            insert into internal.tab_event(created, modified, uuid, group_uuid, qualifier, body, status)
            values($1, $2, $3, $4, $5, $6, $7)
            """;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> insertEvent(final SqlConnection sqlConnection, final EventModel event) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (event == null) {
            throw new ServerSideBadRequestException("event is null");
        }

        return upsertQuery(sqlConnection, event)
                .invoke(voidItem -> log.info("Event was inserted, event={}", event))
                .onFailure(PgException.class)
                .transform(t -> new ServerSideConflictException(String
                        .format("unhandled PgException, %s, event=%s", t.getMessage(), event)));
    }

    Uni<Void> upsertQuery(final SqlConnection sqlConnection, final EventModel event) {
        try {
            var bodyString = objectMapper.writeValueAsString(event.getBody());
            return sqlConnection.preparedQuery(sql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(event.getCreated().atOffset(ZoneOffset.UTC));
                        add(event.getModified().atOffset(ZoneOffset.UTC));
                        add(event.getUuid());
                        add(event.getGroup());
                        add(event.getQualifier());
                        add(bodyString);
                        add(event.getStatus());
                    }}))
                    .replaceWithVoid();
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
