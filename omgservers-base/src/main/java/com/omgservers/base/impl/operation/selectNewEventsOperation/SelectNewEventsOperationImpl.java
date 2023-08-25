package com.omgservers.base.impl.operation.selectNewEventsOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.EventStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectNewEventsOperationImpl implements SelectNewEventsOperation {

    static private final String sql = """
            select id, created, modified, group_id, qualifier, body, status
            from internal.tab_event
            where status = $1
            order by id asc
            limit $2
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<List<EventModel>> selectNewEvents(final SqlConnection sqlConnection, final int limit) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(EventStatusEnum.NEW, limit))
                .map(RowSet::iterator)
                .map(iterator -> {
                    final var events = new ArrayList<EventModel>();
                    while (iterator.hasNext()) {
                        final var event = createEvent(iterator.next());
                        events.add(event);
                    }
                    if (events.size() > 0) {
                        log.info("New events were selected, count={}, events={}", events.size(), events);
                    }
                    return events;
                });
    }

    EventModel createEvent(Row row) {
        EventModel event = new EventModel();
        event.setId(row.getLong("id"));
        event.setCreated(row.getOffsetDateTime("created").toInstant());
        event.setModified(row.getOffsetDateTime("modified").toInstant());
        event.setGroupId(row.getLong("group_id"));
        final var qualifier = EventQualifierEnum.valueOf(row.getString("qualifier"));
        event.setQualifier(qualifier);
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            event.setBody(body);
        } catch (IOException e) {
            log.error("event can't be parsed, id=" + event.getId(), e);
        }
        event.setStatus(EventStatusEnum.valueOf(row.getString("status")));
        return event;
    }
}
