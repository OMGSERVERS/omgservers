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

    static public void validate(EventModel event) {
        if (event == null) {
            throw new IllegalArgumentException("event is null");
        }
    }

    Long id;
    Instant created;
    Instant modified;
    Long groupId;
    EventQualifierEnum qualifier;
    EventBodyModel body;
    EventStatusEnum status;
}
