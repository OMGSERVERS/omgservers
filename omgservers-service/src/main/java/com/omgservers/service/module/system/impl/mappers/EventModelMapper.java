package com.omgservers.service.module.system.impl.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.EventStatusEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EventModelMapper {

    final ObjectMapper objectMapper;

    public EventModel createEvent(Row row) {
        EventModel event = new EventModel();
        event.setId(row.getLong("id"));
        event.setCreated(row.getOffsetDateTime("created").toInstant());
        event.setModified(row.getOffsetDateTime("modified").toInstant());
        event.setGroupId(row.getLong("group_id"));
        final var qualifier = EventQualifierEnum.valueOf(row.getString("qualifier"));
        event.setQualifier(qualifier);
        event.setRelayed(row.getBoolean("relayed"));
        try {
            final var body = objectMapper.readValue(row.getString("body"), qualifier.getBodyClass());
            event.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException("event body can't be parsed, event=" + event, e);
        }
        event.setStatus(EventStatusEnum.valueOf(row.getString("status")));
        return event;
    }
}
