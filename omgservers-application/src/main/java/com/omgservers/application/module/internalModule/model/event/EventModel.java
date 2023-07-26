package com.omgservers.application.module.internalModule.model.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(using = EventDeserializer.class)
public class EventModel {

    static public EventModel create(Long id, Long group, EventQualifierEnum qualifier, EventBodyModel body) {
        Instant now = Instant.now();
        EventModel event = new EventModel();
        event.setId(id);
        event.setCreated(now);
        event.setModified(now);
        event.setGroupId(group);
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

    Long id;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    Long groupId;
    EventQualifierEnum qualifier;
    EventBodyModel body;
    EventStatusEnum status;
}
