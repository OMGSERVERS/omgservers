package com.omgservers.service.module.system.impl.mappers;

import com.omgservers.model.event.EventProjectionModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.EventStatusEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EventProjectionModelMapper {

    public EventProjectionModel fromRow(Row row) {
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
