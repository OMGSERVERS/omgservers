package com.omgservers.application.module.internalModule.impl.operation.selectEventOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.module.internalModule.model.event.EventQualifierEnum;
import com.omgservers.application.module.internalModule.model.event.EventStatusEnum;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectEventOperationImpl implements SelectEventOperation {

    static private final String sql = """
            select created, modified, uuid, group_uuid, qualifier, body, status
            from internal.tab_event
            where uuid = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<EventModel> selectEvent(final SqlConnection sqlConnection,
                                       final UUID uuid) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(uuid))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var event = createEvent(iterator.next());
                            log.info("Event was found, event={}", uuid);
                            return event;
                        } catch (IOException e) {
                            throw new ServerSideConflictException("event can't be parsed, uuid=" + uuid, e);
                        }
                    } else {
                        throw new ServerSideNotFoundException("event was not found, uuids=" + uuid);
                    }
                });
    }

    EventModel createEvent(Row row) throws IOException {
        EventModel event = new EventModel();
        event.setCreated(row.getOffsetDateTime("created").toInstant());
        event.setModified(row.getOffsetDateTime("modified").toInstant());
        event.setUuid(row.getUUID("uuid"));
        event.setGroup(row.getUUID("group_uuid"));
        final var qualifier = EventQualifierEnum.valueOf(row.getString("qualifier"));
        event.setQualifier(qualifier);
        final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
        event.setBody(body);
        event.setStatus(EventStatusEnum.valueOf(row.getString("status")));
        return event;
    }
}
