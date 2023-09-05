package com.omgservers.module.internal.impl.operation.selectEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectEventOperationImpl implements SelectEventOperation {

    static private final String SQL = """
            select id, created, modified, group_id, qualifier, body, status
            from internal.tab_event
            where id = $1
            limit 1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<EventModel> selectEvent(final SqlConnection sqlConnection,
                                       final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        return sqlConnection.preparedQuery(SQL)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var event = createEvent(iterator.next());
                            log.info("Event was found, id={}", id);
                            return event;
                        } catch (IOException e) {
                            throw new ServerSideConflictException("event can't be parsed, id=" + id, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("event was not found, id=" + id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    EventModel createEvent(Row row) throws IOException {
        EventModel event = new EventModel();
        event.setId(row.getLong("id"));
        event.setCreated(row.getOffsetDateTime("created").toInstant());
        event.setModified(row.getOffsetDateTime("modified").toInstant());
        event.setGroupId(row.getLong("group_id"));
        final var qualifier = EventQualifierEnum.valueOf(row.getString("qualifier"));
        event.setQualifier(qualifier);
        final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
        event.setBody(body);
        event.setStatus(EventStatusEnum.valueOf(row.getString("status")));
        return event;
    }
}
