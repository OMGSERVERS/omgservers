package com.omgservers.application.module.internalModule.model.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = EventDeserializer.class)
public class EventModel {

    static public EventModel create(UUID group, EventQualifierEnum qualifier, EventBodyModel body) {
        return create(UUID.randomUUID(), group, qualifier, body);
    }

    static public EventModel create(UUID uuid, UUID group, EventQualifierEnum qualifier, EventBodyModel body) {
        Instant now = Instant.now();
        EventModel event = new EventModel();
        event.setCreated(now);
        event.setModified(now);
        event.setUuid(uuid);
        event.setGroup(group);
        event.setQualifier(qualifier);
        event.setBody(body);
        event.setStatus(EventStatusEnum.NEW);
        return event;
    }

    static public void validate(EventModel event) {
        if (event == null) {
            throw new IllegalArgumentException("event is null");
        }
    }

    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    UUID uuid;
    UUID group;
    EventQualifierEnum qualifier;
    EventBodyModel body;
    EventStatusEnum status;
}
