package com.omgservers.service.server.event.mapper;

import com.omgservers.service.event.EventProjectionModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.EventStatusEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EventProjectionModelMapper {

    public EventProjectionModel fromRow(final Row row) {
        final var eventProjection = new EventProjectionModel();
        eventProjection.setId(row.getLong("id"));
        eventProjection.setCreated(row.getOffsetDateTime("created").toInstant());
        eventProjection.setModified(row.getOffsetDateTime("modified").toInstant());
        eventProjection.setDelayed(row.getOffsetDateTime("delayed").toInstant());
        final var qualifier = EventQualifierEnum.valueOf(row.getString("qualifier"));
        eventProjection.setQualifier(qualifier);
        eventProjection.setStatus(EventStatusEnum.valueOf(row.getString("status")));
        eventProjection.setDeleted(row.getBoolean("deleted"));
        return eventProjection;
    }
}
