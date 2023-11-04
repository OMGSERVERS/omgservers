package com.omgservers.service.module.system.impl.mappers;

import com.omgservers.model.eventProjection.EventProjectionModel;
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
        eventProjection.setGroupId(row.getLong("group_id"));
        return eventProjection;
    }
}
